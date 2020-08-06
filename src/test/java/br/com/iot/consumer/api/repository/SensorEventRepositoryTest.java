package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.ApplicationStartup;
import br.com.iot.consumer.api.config.database.H2DatabaseConfig;
import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.repository.sql.AggregateSqlFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStartup.class, SensorEventRepository.class, H2DatabaseConfig.class})
/** Tests for  {@link SensorEventRepository} */
class SensorEventRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventRepositoryTest.class);

    @Autowired
    private SensorEventRepository testClass;

    @Autowired
    private DatabaseClient databaseClient;

    @MockBean
    private AggregateSqlFactory aggregateSqlFactory;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        databaseClient.execute(Files.readString(Path.of(getClass().getResource("/db/sql/sensor-event/create-table.sql").toURI())))
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        databaseClient.execute(Files.readString(Path.of(getClass().getResource("/db/sql/sensor-event/insert-multiple.sql").toURI())))
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
        databaseClient.execute("DROP TABLE sensor_event")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void testSave() {
        final LocalDateTime timestamp = LocalDateTime.of(2020, 10, 1, 10, 10);
        SensorEventEntity eventEntity = new SensorEventEntity(timestamp, 12345L, "TEMPERATURE", BigDecimal.TEN, "Test");
        StepVerifier.create(testClass.save(eventEntity)).verifyComplete();

        databaseClient.execute("SELECT COUNT(*) FROM sensor_event WHERE sensor_id = 12345 AND name = 'Test' AND type ='TEMPERATURE' and value = 10").fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testFindBySensorIdWithFiltersStartAndEndDateOnly() {
        SearchSensorEventsRequest.FilterEvents filterEvents = new SearchSensorEventsRequest.FilterEvents();
        filterEvents.setStartDate(LocalDateTime.of(2020, 8, 3, 12, 12));
        filterEvents.setEndDate(LocalDateTime.of(2020, 8, 5, 12, 12));
        SearchSensorEventsRequest searchRequest = new SearchSensorEventsRequest();
        searchRequest.setFilter(filterEvents);
        searchRequest.setPage(new SearchSensorEventsRequest.PageAndSortEvents());

        StepVerifier.create(testClass.findBySensorIdWithFilters(88L, searchRequest)).expectNextCount(2).verifyComplete();

    }
}