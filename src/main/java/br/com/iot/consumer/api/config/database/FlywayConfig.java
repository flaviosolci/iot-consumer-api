package br.com.iot.consumer.api.config.database;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is workaround since flyway does not have support to r2dbc
 * See: https://stackoverflow.com/questions/59553647/how-to-run-flyway-migration-for-reactive-r2dbc-driver-on-sprintboot-stratup/
 * See more: https://github.com/flyway/flyway/issues/2502
 */
@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    Flyway flyway(@Value("${spring.flyway.url}") String url,
                  @Value("${spring.flyway.user}") String user,
                  @Value("${spring.flyway.password}") String pass) {
        return new Flyway(Flyway.configure()
                .validateOnMigrate(true)
                .dataSource(url, user, pass));
    }

}
