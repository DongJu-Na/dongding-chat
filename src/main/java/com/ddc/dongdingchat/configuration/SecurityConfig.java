package com.ddc.dongdingchat.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import com.ddc.dongdingchat.configuration.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
  public MapReactiveUserDetailsService userDetailsRepository() {
      User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
      UserDetails user = userBuilder.username("user").password("user").roles("USER").build();
      UserDetails admin = userBuilder.username("admin").password("admin").roles("USER", "ADMIN").build();
      return new MapReactiveUserDetailsService(user, admin);
  }
  
  private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication,
      																AuthorizationContext context) {

			return authentication
								.map(a -> context.getVariables().get("user").equals(a.getName()))
								.map(AuthorizationDecision::new);
		}
  
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
																												  JwtTokenProvider tokenProvider,
																												  ReactiveAuthenticationManager reactiveAuthenticationManager) {
		http
			.authorizeExchange(exchanges -> exchanges
			    .anyExchange().authenticated()
			)
			.authenticationManager(reactiveAuthenticationManager)
			.httpBasic(withDefaults())
			.formLogin(withDefaults());
		return http.build();
	}
	
  @Bean
  public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                     PasswordEncoder passwordEncoder) {
      var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
      authenticationManager.setPasswordEncoder(passwordEncoder);
      return authenticationManager;
  }
  

    
}

