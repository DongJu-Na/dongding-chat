package com.ddc.dongdingchat.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tutorial {
  
  @Id
  private int id;

  private String title;

  private String description;

  private boolean published;


}
