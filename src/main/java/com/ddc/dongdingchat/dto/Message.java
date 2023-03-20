package com.ddc.dongdingchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data  
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
	private String username;
	private String message;
	private String sessionId;
	
}