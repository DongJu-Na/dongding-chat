package com.ddc.dongdingchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebFlux
@SpringBootApplication
public class DongdingChatApplication {

  @Bean
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
  		log.debug("ConnectionFactory Load Start");
  	
      ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
      initializer.setConnectionFactory(connectionFactory);

      CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
      populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")));
      populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/data.sql")));
      initializer.setDatabasePopulator(populator);
      
      log.debug("ConnectionFactory Load End");
      return initializer;
  }
	
	public static void main(String[] args) {
		SpringApplication.run(DongdingChatApplication.class, args);
	}


}
