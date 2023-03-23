package com.ddc.dongdingchat.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
  private Long id;
  private String username;
  private String password;
  private List<String> roles;
  private String firstName;
  private String lastName;
  private boolean enabled;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
}
