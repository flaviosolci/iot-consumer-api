package br.com.iot.consumer.api.controller;

import br.com.iot.consumer.api.ApplicationStarter;
import br.com.iot.consumer.api.config.security.WebSecurityConfigStub;
import br.com.iot.consumer.api.service.SensorEventsQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.*;

/** Test for {@link SensorEventsController} */
@WebFluxTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationStarter.class, WebSecurityConfigStub.class})
class SensorEventsControllerTest {

    private WebTestClient webClient;

    @MockBean
    private SensorEventsQueryService eventsQueryService;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        webClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("/events")
                .build();
        when(eventsQueryService.aggregateAllWithFilter(any())).thenReturn(Flux.empty());
        when(eventsQueryService.findAllWithFilter(any())).thenReturn(Flux.empty());
    }

    // =======================
    // == FIND ALL
    // =======================

    @Test
    void testFindAllOK() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllMissingStartDate() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllMissingEndDate() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllMissingBothDate() {
        webClient.get()
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithType() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("filter.eventType", "Test")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorId() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("filter.sensorId", 12345L)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithLimit() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.limit", 10)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithLimitInvalidBigger() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.limit", 10000)
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithLimitInvalidSmaller() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.limit", -1)
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithOffset() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.offset", 2)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithOffsetInvalidSmaller() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.offset", -12)
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithSortByInvalid() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.sortBy", "a")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithDirection() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.direction", "ASC")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).findAllWithFilter(any());
    }

    @Test
    void testFindAllWithSensorWithDirectionInvalid() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("page.direction", "a")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).findAllWithFilter(any());
    }

    // =======================
    // == AGGREGATE
    // =======================

    @Test
    void testAggregateOK() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateOKMissingStartDate() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateOKMissingEndDate() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateOKMissingBothDate() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).aggregateAllWithFilter(any());
    }


    @Test
    void testAggregateMissingGroupBy() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.type", "max")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).aggregateAllWithFilter(any());
    }


    @Test
    void testAggregateMissingType() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .build())
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody();

        verify(eventsQueryService, never()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateSortBy() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .queryParam("aggregate.sortBy", "name")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateDirection() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .queryParam("aggregate.direction", "asc")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).aggregateAllWithFilter(any());
    }

    @Test
    void testAggregateSensorId() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/aggregate")
                        .queryParam("filter.startDate", "2020-07-10T13:10:10")
                        .queryParam("filter.endDate", "2020-07-10T13:10:10")
                        .queryParam("aggregate.groupBy", "type")
                        .queryParam("aggregate.type", "max")
                        .queryParam("filter.sensorId", 124)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        verify(eventsQueryService, only()).aggregateAllWithFilter(any());
    }
}