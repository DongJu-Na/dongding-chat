package com.ddc.dongdingchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class DongdingChatApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DongdingChatApplication.class, args);
	}


}
