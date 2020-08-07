package br.com.iot.consumer.api.stream.consumer;

//@WebFluxTest

import br.com.iot.consumer.api.service.SensorEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Tests for {@link SensorEventConsumer} */
@ExtendWith(MockitoExtension.class)
class SensorEventConsumerTest {

    private SensorEventConsumer testClass;
    @Mock
    private SensorEventService eventService;

    @BeforeEach
    void setUp() {
        testClass = new SensorEventConsumer(eventService);
        when(eventService.processEvent(any())).thenReturn(Flux.empty());
    }

    @Test
    void testConsume() {
        testClass.saveEvent().accept(Flux.empty());

        verify(eventService, only()).processEvent(any());
    }
}