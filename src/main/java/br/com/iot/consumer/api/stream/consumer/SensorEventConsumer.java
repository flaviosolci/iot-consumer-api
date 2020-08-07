package br.com.iot.consumer.api.stream.consumer;

import br.com.iot.consumer.api.model.event.SensorEvent;
import br.com.iot.consumer.api.service.SensorEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration
public class SensorEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventConsumer.class);

    private final SensorEventService sensorEventService;

    public SensorEventConsumer(SensorEventService sensorEventService) {
        this.sensorEventService = sensorEventService;
    }

    @Bean
    public Consumer<Flux<SensorEvent>> saveEvent() {
        return this::processEvent;
    }

    private void processEvent(Flux<SensorEvent> sensorEventFlux) {
        sensorEventService.processEvent(sensorEventFlux)
                .subscribe(null,
                        throwable -> LOG.error("===== Failed to save event {}", sensorEventService),
                        () -> LOG.debug("==== Finished the process of the event {}", sensorEventService));
    }
}
