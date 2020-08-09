package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MedianSqlStrategy extends BaseSqlStrategy {

    @Override
    public Flux<AggregateSensorEventDto> execute(DatabaseClient databaseClient, AggregateEventsFilter sensorEventsRequest) {
        return databaseClient.execute(prepareSql(sensorEventsRequest))
                .bind("start_date", sensorEventsRequest.getFilter().getStartDate())
                .bind("end_date", sensorEventsRequest.getFilter().getEndDate())
                .map(this::mapAggregation)
                .all();
    }

    @Override
    public AggregateFunctionType getType() {
        return AggregateFunctionType.MEDIAN;
    }

    private String prepareSql(AggregateEventsFilter sensorEventsRequest) {
        return String.format("SELECT percentile_cont(0.5) WITHIN GROUP (ORDER BY value) as aggregate_result, count(*), %s " +
                        "  FROM sensor_event " +
                        "  WHERE (timestamp BETWEEN :start_date AND :end_date)" +
                        "  GROUP BY %s ",
                sensorEventsRequest.getAggregate().getGroupByCommaDelimited(),
                sensorEventsRequest.getAggregate().getGroupByCommaDelimited());
    }

}
