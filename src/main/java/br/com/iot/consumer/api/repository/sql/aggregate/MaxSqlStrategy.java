package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MaxSqlStrategy extends BaseSqlStrategy {

    @Override
    public Flux<AggregateSensorEventDto> execute(DatabaseClient databaseClient, AggregateSensorEventsRequest sensorEventsRequest) {
        return databaseClient.execute(prepareSql(sensorEventsRequest))
                .bind("start_date", sensorEventsRequest.getStartDate())
                .bind("end_date", sensorEventsRequest.getEndDate())
                .map(this::mapAggregation)
                .all();
    }

    @Override
    public AggregateFunctionType getType() {
        return AggregateFunctionType.MAX;
    }

    private String prepareSql(AggregateSensorEventsRequest sensorEventsRequest) {
        return String.format("SELECT MAX(value) as aggregate_result, count(*), %s " +
                        "  FROM sensor_event " +
                        "  WHERE (timestamp BETWEEN :start_date AND :end_date)" +
                        "  GROUP BY %s ",
                sensorEventsRequest.getGroupByCommaDelimited(),
                sensorEventsRequest.getGroupByCommaDelimited());
    }

}
