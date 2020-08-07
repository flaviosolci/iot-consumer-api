package br.com.iot.consumer.api.controller;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.controller.response.AggregateSensorEventResponse;
import br.com.iot.consumer.api.controller.response.SensorEventResponse;
import br.com.iot.consumer.api.service.SensorEventsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/events")
public class SensorEventsController {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventsController.class);

    private final SensorEventsQueryService sensorEventsQueryService;

    public SensorEventsController(SensorEventsQueryService sensorEventsQueryService) {
        this.sensorEventsQueryService = sensorEventsQueryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SensorEventResponse> findAll(@Valid EventsFilter eventsFilter) {
        return sensorEventsQueryService.findAllWithFilter(eventsFilter)
                .doFirst(() -> LOG.info("==== Finding all events with the filters {}", eventsFilter))
                .doOnComplete(() -> LOG.info("==== Returning all events with the filters {}", eventsFilter));
    }

    @GetMapping(path = "/aggregate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AggregateSensorEventResponse> aggregate(@Valid AggregateEventsFilter eventsFilter) {
        return sensorEventsQueryService.aggregateAllWithFilter(eventsFilter)
                .doFirst(() -> LOG.info("==== Aggregate all events with the request {}", eventsFilter))
                .doOnComplete(() -> LOG.info("==== Returning all events for request {}", eventsFilter));
    }
}