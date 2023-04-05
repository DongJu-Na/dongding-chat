package com.ddc.dongdingchat.configuration;

import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.SSL;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;
import static io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL;

import static io.r2dbc.spi.ConnectionFactoryOptions.builder;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class H2R2dbcConfig extends AbstractR2dbcConfiguration {

  @Bean
  @Override
  public ConnectionFactory connectionFactory() {
    ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
        .option(DRIVER, "h2")
        .option(HOST, "localhost")
        .option(PROTOCOL,"mem") // mem : 인메모리 file : 로컬 파일 경로 
        .option(USER, "sa")
        .option(PASSWORD, "")
        .option(DATABASE, "r2dbc:h2:mem:///testdb")
        .option(SSL, true)
        .option(Option.valueOf("sendStringParametersAsUnicode"), false)
        .build();
    return ConnectionFactories.get(options);
  }

  @Bean
  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    
    CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")));
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db/data.sql")));
    initializer.setDatabasePopulator(populator);
    
    //initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource((
    //    "use database;"
    //        + "DROP TABLE IF EXISTS person;"
   //        + "CREATE TABLE person (id INT IDENTITY(1,1) PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE);")
   //     .getBytes())));
    return initializer;
  }
}