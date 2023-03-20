package com.ddc.dongdingchat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ViewController {
	
	@GetMapping(value="/index")
	public String index() {
    log.debug("index Page access");
		return "index";
	}
	
	@GetMapping(value="/card")
	public String card() {
		log.debug("card Page access");
		return "card";
	}
	
	@GetMapping(value="/channel")
	public String channel() {
		log.debug("channel Page access");
		return "channel";
	}
	
	@GetMapping(value="/")
	public String chat() {
		log.debug("chat Page access");
		return "chat";
	}
	
}
