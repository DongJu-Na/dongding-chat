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
	public String prompt() {
		log.debug("card Page access");
		return "card";
	}
	
	@GetMapping(value="/channel")
	public String demo() {
		log.debug("channel Page access");
		return "channel";
	}
	
}
