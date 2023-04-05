package com.ddc.dongdingchat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class H2ConnectionFactories {
    static ConnectionFactory fromUrl() {
        return ConnectionFactories.get("r2dbc:h2:mem:///test?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
    }

    @Bean
    static ConnectionFactory inMemory() {
    	log.info("ConnectionFactory inMemory Load");
        return H2ConnectionFactory.inMemory("test");
    }

    static ConnectionFactory file() {
    	log.info("ConnectionFactory File Load");
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        //.inMemory("testdb")
                        .file("./testdb")
                        .username("sa")
                        .password("password")
                        .build()
        );
    }

}
