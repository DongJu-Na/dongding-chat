package com.ddc.dongdingchat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
  private static final String[] AUTH_WHITELIST = {
      "/resources/**",
      "/favicon.ico",
      "/login**",
  };
	
	public SecurityConfig() {
		log.info("SecurityConfig Load");
	}
	
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
      UserDetails user = User.withDefaultPasswordEncoder()
          .username("user")
          .password("user")
          .roles("USER")
          .build();
      return new MapReactiveUserDetailsService(user);
  }
  

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange(exchanges -> exchanges
			    .anyExchange().authenticated()
			)
			.httpBasic(withDefaults())
			.formLogin(withDefaults());
		return http.build();
	}
  

    
}

