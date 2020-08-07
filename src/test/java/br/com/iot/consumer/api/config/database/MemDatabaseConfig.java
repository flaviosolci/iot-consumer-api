package br.com.iot.consumer.api.config.database;

import io.r2dbc.spi.ConnectionFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class MemDatabaseConfig {

    public static DatabaseClient databaseClient() {
        return DatabaseClient.create(ConnectionFactories.get("r2dbc:h2:mem:///iot_db;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1"));
    }

    @Bean
    DatabaseClient databaseClientInternal() {
        return MemDatabaseConfig.databaseClient();
    }
}
