package com.ddc.dongdingchat.configuration;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@Component
public class WebSocketChatHandler implements WebSocketHandler {
	 private final ConcurrentHashMap<String, Sinks.Many<String>> channelMessageSinkMap = new ConcurrentHashMap<>();
    
    public WebSocketChatHandler() {
			log.info("WebSocketChatHandler load");
		}
    
    @Override
    public List<String> getSubProtocols() {
    	return Arrays.asList("test");
    }

    @SuppressWarnings("serial")
		@Override
    public Mono<Void> handle(WebSocketSession session) {
        URI uri = session.getHandshakeInfo().getUri();
        String[] segments = uri.getPath().split("/");
        String channel = segments[segments.length - 1];

        Sinks.Many<String> sink = channelMessageSinkMap.computeIfAbsent(channel, k -> Sinks.many().multicast().onBackpressureBuffer());
        
        ObjectMapper objectMapper = new ObjectMapper();
        String enterMessage = "";
        try {
        	enterMessage = objectMapper.writeValueAsString(new HashMap<String, String>() {{
					    put("user", "notice");
					    put("text",  String.format("%s 님이 입장하였습니다.", session.getId()));
					}});
        	
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
        
        sink.tryEmitNext(enterMessage);

        Mono<Void> input = session.receive()
                .map(msg -> {
                	log.debug(session.getId() + " " + msg.getPayloadAsText());
                	return msg.getPayloadAsText();
                })
                .doOnNext(msg -> sink.tryEmitNext(msg))
                .doOnTerminate(() -> {
                    channelMessageSinkMap.remove(channel);
                    
                    String leaveMessage = "";
                    
                  	try {
											leaveMessage = objectMapper.writeValueAsString(new HashMap<String, String>() {{
											  put("user", "notice");
											  put("text",  String.format("%s 님이 퇴장하였습니다.", session.getId()));
											}});
										} catch (JsonProcessingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                  	
                    sink.tryEmitNext(leaveMessage);
                })
                .then();

        Mono<Void> output = session.send(Flux.from(sink.asFlux()).map(session::textMessage));

        return Mono.zip(input, output).then();
    }
}
