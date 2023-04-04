package com.ddc.dongdingchat.configuration;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class H2ServerConfig {
  @Value("${h2.console.port}")
  private Integer port;
  private Server webServer;

  @EventListener(ContextRefreshedEvent.class)
  public void start() throws java.sql.SQLException {
      log.info("H2 In-Memory Database 시작 사용 포트 > {}.", port);
      this.webServer = Server.createWebServer("-webPort", port.toString()).start();
  }

  @EventListener(ContextClosedEvent.class)
  public void stop() {
      log.info("H2 In-Memory Database 중지 사용 포트 > {}.", port); this.webServer.stop();
  }
}
