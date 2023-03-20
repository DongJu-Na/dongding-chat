package com.ddc.dongdingchat;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketChatHandler implements WebSocketHandler {
    private final ConcurrentHashMap<String, Sinks.Many<String>> userMessageSinkMap = new ConcurrentHashMap<>();
    
    public WebSocketChatHandler() {
			log.info("WebSocketChatHandler load");
		}
    
    @Override
    public List<String> getSubProtocols() {
    	return Arrays.asList("test");
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();
        userMessageSinkMap.put(session.getId(), sink);
        
        // 새로운 사용자가 입장하면, 다른 사용자들에게 입장 메시지를 전송
        String enterMessage = String.format("%s 님이 입장하였습니다.", session.getId());
        userMessageSinkMap.values().forEach(s -> s.tryEmitNext(enterMessage));

        Mono<Void> input = session.receive()
                .map(msg -> session.getId() + ": " + msg.getPayloadAsText())
                .doOnNext(msg -> userMessageSinkMap.values().forEach(s -> s.tryEmitNext(msg)))
                .doOnTerminate(() -> {
                	userMessageSinkMap.remove(session.getId());
                	
                	 // 새로운 사용자가 퇴장하면, 다른 사용자들에게 퇴장 메시지를 전송
                  String leaveMessage = String.format("%s 님이 퇴장하였습니다.", session.getId());
                  userMessageSinkMap.values().forEach(s -> s.tryEmitNext(leaveMessage));
                })
                .then();

        Mono<Void> output = session.send(Flux.from(sink.asFlux()).map(session::textMessage));

        return Mono.zip(input, output).then();
    }
}
