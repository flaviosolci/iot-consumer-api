package br.com.iot.consumer.api.stream.consumer;

import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.model.event.SensorEvent;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableBinding(Sink.class)
public class SensorEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventConsumer.class);

    private final SensorEventMapper eventMapper;
    private final SensorEventRepository sensorEventRepository;

    public SensorEventConsumer(SensorEventMapper eventMapper, SensorEventRepository sensorEventRepository) {
        this.eventMapper = eventMapper;
        this.sensorEventRepository = sensorEventRepository;
    }

    @StreamListener(Sink.INPUT)
    public void saveEvent(SensorEvent sensorEvent) {
        Mono.just(sensorEvent)
                .doFirst(() -> LOG.debug("==== Received the event {}", sensorEvent))
                .map(eventMapper::toEntity)
                .doOnNext(entity -> LOG.debug("==== New entity -> {}", entity))
                .flatMap(sensorEventRepository::save)
                .subscribe(null, throwable -> LOG.error("===== Failed to save event {}", sensorEvent), () -> LOG.debug("==== Finished the process of the event {}", sensorEvent));
    }
}
