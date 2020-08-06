package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

/** Strategy to use aggregate sql functions */
public interface AggregateSqlStrategy {

    Flux<AggregateSensorEventDto> execute(DatabaseClient databaseClient, final AggregateSensorEventsRequest sensorEventsRequest);

    AggregateFunctionType getType();

}
