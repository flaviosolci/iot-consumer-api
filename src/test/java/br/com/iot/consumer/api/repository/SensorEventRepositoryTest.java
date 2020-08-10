package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.repository.sql.AggregateSqlFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/** Tests for  {@link SensorEventRepository} */
@ContextConfiguration(classes = SensorEventRepository.class)
class SensorEventRepositoryTest extends RepositoryTest {

    @Autowired
    private SensorEventRepository testClass;

    @Autowired
    private DatabaseClient databaseClient;

    @MockBean
    private AggregateSqlFactory aggregateSqlFactory;

    @BeforeAll
    static void setUp() throws URISyntaxException, IOException {
        executeSQL("sensor-event/create-table.sql");
        executeSQL("sensor-event/insert-multiple.sql");
    }

    @Test
    void testSave() {
        final var timestamp = OffsetDateTime.of(2020, 10, 1, 10, 10, 10, 10, ZoneOffset.UTC);
        final var eventEntity = new SensorEventEntity(timestamp, 12345L, "TEMPERATURE", BigDecimal.TEN, "Test");
        StepVerifier.create(testClass.save(eventEntity)).verifyComplete();

        databaseClient.execute("SELECT COUNT(*) FROM sensor_event WHERE sensor_id = 12345 AND name = 'Test' AND type ='TEMPERATURE' and value = 10").fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testFindBySensorIdWithFiltersStartAndEndDateOnly() {
        final var filterEvents = new EventsFilter.Filter();
        filterEvents.setStartDate(LocalDateTime.of(2020, 8, 3, 12, 12));
        filterEvents.setEndDate(LocalDateTime.of(2020, 8, 5, 12, 12));
        filterEvents.setSensorId(88L);

        final var searchRequest = new EventsFilter();
        searchRequest.setFilter(filterEvents);
        searchRequest.setPage(new EventsFilter.PageAndSort());

        StepVerifier.create(testClass.findBySensorIdWithFilters(searchRequest)).expectNextCount(2).verifyComplete();

    }

    @Test
    void testFindBySensorIdWithFiltersType() {
        final var filterEvents = new EventsFilter.Filter();
        filterEvents.setStartDate(LocalDateTime.of(2020, 8, 3, 12, 12));
        filterEvents.setEndDate(LocalDateTime.of(2020, 8, 5, 12, 12));
        filterEvents.setEventType("TEMPERATURE");
        filterEvents.setSensorId(88L);

        final var searchRequest = new EventsFilter();
        searchRequest.setFilter(filterEvents);
        searchRequest.setPage(new EventsFilter.PageAndSort());

        StepVerifier.create(testClass.findBySensorIdWithFilters(searchRequest)).expectNextCount(1).verifyComplete();

    }

    @Test
    void testFindBySensorIdWithFiltersPageLimit() {
        final var filterEvents = new EventsFilter.Filter();
        filterEvents.setStartDate(LocalDateTime.of(2020, 8, 3, 12, 12));
        filterEvents.setEndDate(LocalDateTime.of(2020, 8, 5, 12, 12));
        filterEvents.setSensorId(88L);

        final var page = new EventsFilter.PageAndSort();
        page.setLimit(1);

        final var searchRequest = new EventsFilter();
        searchRequest.setFilter(filterEvents);
        searchRequest.setPage(page);

        StepVerifier.create(testClass.findBySensorIdWithFilters(searchRequest)).expectNextCount(1).verifyComplete();

    }

    @Test
    void testFindBySensorIdWithFiltersPageOffset() {
        final var filterEvents = new EventsFilter.Filter();
        filterEvents.setStartDate(LocalDateTime.of(2020, 8, 3, 12, 12));
        filterEvents.setEndDate(LocalDateTime.of(2020, 8, 5, 12, 12));
        filterEvents.setSensorId(88L);

        final var page = new EventsFilter.PageAndSort();
        page.setOffset(2);

        final var searchRequest = new EventsFilter();
        searchRequest.setFilter(filterEvents);
        searchRequest.setPage(page);

        StepVerifier.create(testClass.findBySensorIdWithFilters(searchRequest)).verifyComplete();

    }
}