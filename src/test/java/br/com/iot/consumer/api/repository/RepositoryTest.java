package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.ApplicationStarter;
import br.com.iot.consumer.api.config.database.MemDatabaseConfig;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStarter.class, SensorEventRepository.class, MemDatabaseConfig.class})
public abstract class RepositoryTest {

    private static final DatabaseClient databaseClient = MemDatabaseConfig.databaseClient();
    private static final String DEFAULT_FOLDER = "/db/sql/test";

    protected static void executeSQL(String filePath) throws IOException, URISyntaxException {
        final var resource = SensorEventRepositoryTest.class.getResource("/db/sql/test/" + filePath);
        databaseClient.execute(Files.readString(Path.of(resource.toURI())))
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @AfterAll
    static void tearDown() {
        databaseClient.execute("DROP ALL OBJECTS")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

    }
}
