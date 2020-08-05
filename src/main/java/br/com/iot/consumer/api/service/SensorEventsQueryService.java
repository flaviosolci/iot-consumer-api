package br.com.iot.consumer.api.service;

import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.controller.response.AggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.SensorEventResponse;
import br.com.iot.consumer.api.exception.InvalidValueException;
import br.com.iot.consumer.api.mapper.SensorEventMapper;
import br.com.iot.consumer.api.repository.SensorEventRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class SensorEventsQueryService {

    private final SensorEventRepository sensorEventRepository;
    private final SensorEventMapper sensorEventMapper;

    public SensorEventsQueryService(SensorEventRepository sensorEventRepository, SensorEventMapper sensorEventMapper) {
        this.sensorEventRepository = sensorEventRepository;
        this.sensorEventMapper = sensorEventMapper;
    }

    public Flux<SensorEventResponse> findBySensorId(Long sensorId, SearchSensorEventsRequest filterRequest) {
        return sensorEventRepository.findBySensorIdWithFilters(sensorId, filterRequest)
                .map(sensorEventMapper::toResponse);
    }

    public Flux<AggregateSensorEventResponse> aggregate(AggregateSensorEventsRequest aggregateSensorEventsRequest) throws InvalidValueException {
        return sensorEventRepository.aggregateAsPerRequest(aggregateSensorEventsRequest)
                .map(sensorEventMapper::toResponse);
    }
}
