package br.com.iot.consumer.api.service;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.controller.response.ImmutableAggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.ImmutableSensorEventResponse;
import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.model.dto.ImmutableAggregateSensorEventDto;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Tests for {@link SensorEventsQueryService} */
@ExtendWith(MockitoExtension.class)
class SensorEventsQueryServiceTest {

    private SensorEventsQueryService testClass;
    @Mock
    private SensorEventRepository eventRepository;

    @BeforeEach
    void setUp() {
        testClass = new SensorEventsQueryService(eventRepository, Mappers.getMapper(SensorEventMapper.class));
    }

    @Test
    void testFindBySensorId() {
        final var timestamp = OffsetDateTime.of(2020, 10, 1, 10, 10, 10, 10, ZoneOffset.UTC);
        final var sensorEventEntity = new SensorEventEntity(timestamp, 1234L, "CPU", BigDecimal.TEN, "test", "{\"json\":\"test\"}");
        when(eventRepository.findBySensorIdWithFilters(any())).thenReturn(Flux.just(sensorEventEntity));

        final var expected = ImmutableSensorEventResponse.builder()
                .name("test")
                .timestamp(timestamp)
                .type("CPU")
                .sensorId(1234L)
                .value(BigDecimal.TEN)
                .build();

        StepVerifier.create(testClass.findAllWithFilter(new EventsFilter()))
                .assertNext(actual -> assertEquals(expected, actual))
                .verifyComplete();

        verify(eventRepository, only()).findBySensorIdWithFilters(any());
    }

    @Test
    void testFindBySensorIdEmpty() {
        when(eventRepository.findBySensorIdWithFilters(any())).thenReturn(Flux.empty());

        StepVerifier.create(testClass.findAllWithFilter(new EventsFilter()))
                .verifyComplete();

        verify(eventRepository, only()).findBySensorIdWithFilters(any());
    }

    @Test
    void testAggregate() {

        final var eventDto = ImmutableAggregateSensorEventDto.builder()
                .type("CPU")
                .value(BigDecimal.TEN)
                .count(100)
                .name("test")
                .function(AggregateFunctionType.MAX)
                .build();

        when(eventRepository.aggregateAsPerRequest(any())).thenReturn(Flux.just(eventDto));

        final var expected = ImmutableAggregateSensorEventResponse.builder()
                .name("test")
                .type("CPU")
                .count(100)
                .value(BigDecimal.TEN)
                .function(AggregateFunctionType.MAX)
                .build();

        StepVerifier.create(testClass.aggregateAllWithFilter(new AggregateEventsFilter()))
                .assertNext(actual -> assertEquals(expected, actual))
                .verifyComplete();

        verify(eventRepository, only()).aggregateAsPerRequest(any());
    }

}