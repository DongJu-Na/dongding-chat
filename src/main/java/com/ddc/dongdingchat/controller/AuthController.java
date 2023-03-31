package com.ddc.dongdingchat.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddc.dongdingchat.configuration.jwt.JwtTokenProvider;
import com.ddc.dongdingchat.dto.AuthenticationRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
	
  private final JwtTokenProvider tokenProvider;
  
  private final ReactiveAuthenticationManager authenticationManager;

  @PostMapping("/login")
  public Mono<ResponseEntity> login(
          @Valid @RequestBody Mono<AuthenticationRequest> authRequest) {

      return authRequest
              .flatMap(login -> this.authenticationManager
                      .authenticate(new UsernamePasswordAuthenticationToken(
                              login.getEmail(), login.getPassword()))
                      .map(this.tokenProvider::createToken))
              .map(jwt -> {
                  HttpHeaders httpHeaders = new HttpHeaders();
                  httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                  var tokenBody = Map.of("access_token", jwt);
                  return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
              });

  }
  
	
}
