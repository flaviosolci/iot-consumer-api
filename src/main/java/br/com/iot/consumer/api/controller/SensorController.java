package br.com.iot.consumer.api.controller;

import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.controller.response.AggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.SensorEventResponse;
import br.com.iot.consumer.api.exception.InvalidValueException;
import br.com.iot.consumer.api.service.SensorEventsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/sensor")
public class SensorController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorController.class);

    private final SensorEventsQueryService sensorEventsQueryService;

    public SensorController(SensorEventsQueryService sensorEventsQueryService) {
        this.sensorEventsQueryService = sensorEventsQueryService;
    }

    @GetMapping(path = "/{sensorId}/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SensorEventResponse> findAll(@PathVariable Long sensorId, @Valid SearchSensorEventsRequest filterRequest) {
        return sensorEventsQueryService.findBySensorId(sensorId, filterRequest)
                .doFirst(() -> LOG.info("==== Finding all events for the sensor {} with the filters {}", sensorId, filterRequest))
                .doOnComplete(() -> LOG.info("==== Returning all events for the sensor {} with the filters {}", sensorId, filterRequest));
    }

    @GetMapping(path = "/events/aggregate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AggregateSensorEventResponse> aggregate(@Valid AggregateSensorEventsRequest aggregateSensorEventsRequest) throws InvalidValueException {
        return sensorEventsQueryService.aggregate(aggregateSensorEventsRequest);
    }

}