package br.com.iot.consumer.api.controller;

import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.service.SensorEventsQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @GetMapping(path = "/events/{sensorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> findAll(@PathVariable Long sensorId, @Valid SearchSensorEventsRequest filterRequest) {
        return Mono.empty();
    }
}

