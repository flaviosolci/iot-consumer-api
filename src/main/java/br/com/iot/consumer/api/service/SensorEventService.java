package br.com.iot.consumer.api.service;

import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.model.event.SensorEvent;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SensorEventService {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventService.class);

    private final SensorEventMapper eventMapper;
    private final SensorEventRepository sensorEventRepository;

    public SensorEventService(SensorEventRepository sensorEventRepository, SensorEventMapper eventMapper) {
        this.eventMapper = eventMapper;
        this.sensorEventRepository = sensorEventRepository;
    }

    public Flux<Void> processEvent(Flux<SensorEvent> sensorEvent) {
        return sensorEvent
                .doFirst(() -> LOG.info("==== Processing events ===="))
                .map(eventMapper::toEntity)
                .doOnNext(entity -> LOG.debug("==== New entity -> {}", entity))
                .flatMap(sensorEventRepository::save)
                .doOnComplete(() -> LOG.info("==== Events processed! ===="));
    }
}
