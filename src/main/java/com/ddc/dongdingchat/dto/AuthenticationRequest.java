package com.ddc.dongdingchat.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
	
	private static final long serialVersionUID = -6417694360179937885L;
	
	@NotBlank
	@NotNull
	private String email;
	
	@NotBlank
	@NotNull
	private String password;
	
 
}
