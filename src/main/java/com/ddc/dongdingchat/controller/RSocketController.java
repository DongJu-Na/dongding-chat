package com.ddc.dongdingchat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.ddc.dongdingchat.dto.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RSocketController {
	private static final List<RSocketRequester> CLIENTS = new ArrayList<>();
	
	 @ConnectMapping
	  public void onConnect(RSocketRequester requester) {
		log.info("RSocket onConnect");
	    requester.rsocket()
	             .onClose()
	             .doFirst(() -> { 
	                 CLIENTS.add(requester);
	               })
	               .doOnError(error -> { 
	               })
	               .doFinally(consumer -> { 
	                 CLIENTS.remove(requester);
	               })
	             .subscribe();
	  }

	  @MessageMapping("message")
	  Mono<Message> message(Message message) {
		log.info("RSocket message");
	    this.sendMsg(message);
	    return Mono.just(message);
	  }

	  @MessageMapping("send")
	  void sendMsg(Message message) {
		log.info("RSocket send");
	    Message msgDto = new Message();
	    msgDto.setUsername(message.getUsername());
	    msgDto.setMessage(message.getMessage());

	    Flux.fromIterable(CLIENTS)
	        .doOnNext(ea -> {
	          ea.route("")
	            .data(msgDto)
	            .send()
	            .subscribe();
	        })
	        .subscribe();
	  }
	
}
