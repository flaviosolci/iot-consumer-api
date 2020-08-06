package br.com.iot.consumer.api.config.database;

import io.r2dbc.spi.ConnectionFactories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class H2DatabaseConfig {

    @Bean
    DatabaseClient databaseClient(@Value("${spring.r2dbc.url}") String url) {

        return DatabaseClient.create(ConnectionFactories.get(url));
    }
}
