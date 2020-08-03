package br.com.iot.consumer.api.stream.consumer;

import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.model.event.SensorEvent;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableBinding(Sink.class)
public class SensorEventConsumer {

    private final SensorEventMapper eventMapper;
    private final SensorEventRepository sensorEventRepository;

    public SensorEventConsumer(SensorEventMapper eventMapper, SensorEventRepository sensorEventRepository) {
        this.eventMapper = eventMapper;
        this.sensorEventRepository = sensorEventRepository;
    }

    @StreamListener(Sink.INPUT)
    public void enrichLogMessage(SensorEvent sensorEvent) {
        Mono.just(sensorEvent)
                .map(eventMapper::toEntity)
                .flatMap(sensorEventRepository::save)
                .subscribe();
    }
}
