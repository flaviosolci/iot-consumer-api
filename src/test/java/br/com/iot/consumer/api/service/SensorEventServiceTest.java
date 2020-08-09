package br.com.iot.consumer.api.service;

import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.model.event.ImmutableSensorEvent;
import br.com.iot.consumer.api.model.event.SensorEvent;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.*;

/** Tests for {@link SensorEventService} */
@ExtendWith(MockitoExtension.class)
class SensorEventServiceTest {

    private SensorEventService testClass;
    @Mock
    private SensorEventRepository eventRepository;

    @BeforeEach
    void setUp() {
        testClass = new SensorEventService(eventRepository, Mappers.getMapper(SensorEventMapper.class));
    }

    @Test
    void processEventEmpty() {
        StepVerifier.create(testClass.processEvent(Flux.just())).verifyComplete();

        verifyNoInteractions(eventRepository);
    }

    @Test
    void processEvent() {
        when(eventRepository.save(any())).thenReturn(Mono.empty());

        final var timestamp = OffsetDateTime.of(2020, 10, 1, 10, 10, 10, 10, ZoneOffset.UTC);

        final var expected = new ImmutableSensorEvent.Builder()
                .name("test")
                .type("CPU")
                .value(BigDecimal.TEN)
                .id(1234L)
                .timestamp(timestamp)
                .build();

        Flux<SensorEvent> events = Flux.just(expected);
        StepVerifier.create(testClass.processEvent(events)).verifyComplete();

        verify(eventRepository, only()).save(any());
    }
}