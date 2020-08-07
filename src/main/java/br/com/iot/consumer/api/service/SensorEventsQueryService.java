package br.com.iot.consumer.api.service;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.controller.response.AggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.SensorEventResponse;
import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@Transactional(readOnly = true)
public class SensorEventsQueryService {

    private final SensorEventRepository sensorEventRepository;
    private final SensorEventMapper sensorEventMapper;

    public SensorEventsQueryService(SensorEventRepository sensorEventRepository, SensorEventMapper sensorEventMapper) {
        this.sensorEventRepository = sensorEventRepository;
        this.sensorEventMapper = sensorEventMapper;
    }

    public Flux<SensorEventResponse> findAllWithFilter(EventsFilter filterRequest) {
        return sensorEventRepository.findBySensorIdWithFilters(filterRequest)
                .map(sensorEventMapper::toResponse);
    }

    public Flux<AggregateSensorEventResponse> aggregateAllWithFilter(AggregateEventsFilter aggregateEventsFilter) {
        return sensorEventRepository.aggregateAsPerRequest(aggregateEventsFilter)
                .map(sensorEventMapper::toResponse);
    }
}
