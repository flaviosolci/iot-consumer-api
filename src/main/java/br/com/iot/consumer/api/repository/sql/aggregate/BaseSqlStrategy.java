package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.dto.ImmutableAggregateSensorEventDto;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public abstract class BaseSqlStrategy implements AggregateSqlStrategy {

    @Override
    public Flux<AggregateSensorEventDto> execute(DatabaseClient databaseClient, AggregateEventsFilter sensorEventsRequest) {
        return databaseClient.execute(prepareSql(sensorEventsRequest))
                .bind("start_date", sensorEventsRequest.getFilter().getStartDate())
                .bind("end_date", sensorEventsRequest.getFilter().getEndDate())
                .map(this::mapAggregation)
                .all();
    }

    protected abstract String getAggregateFunctionSql();

    protected String prepareSql(AggregateEventsFilter sensorEventsRequest) {
        return String.format("SELECT %s as aggregate_result, count(*), %s " +
                        "  FROM sensor_event " +
                        "  WHERE (timestamp BETWEEN :start_date AND :end_date)" +
                        "  %s " +
                        "  GROUP BY %s ",
                getAggregateFunctionSql(),
                sensorEventsRequest.getAggregate().getGroupByCommaDelimited(),
                getOptionalFilters(sensorEventsRequest.getFilter()),
                sensorEventsRequest.getAggregate().getGroupByCommaDelimited());
    }

    private String getOptionalFilters(AggregateEventsFilter.Filter filter) {
        return filter.getOptionalFields().entrySet().stream()
                .map(optionalEntrySet -> String.format(" AND %s=%d", optionalEntrySet.getKey(), optionalEntrySet.getValue()))
                .collect(Collectors.joining(" "));
    }

    public AggregateSensorEventDto mapAggregation(Row row, RowMetadata metadata) {
        return ImmutableAggregateSensorEventDto.builder()
                .count(row.get("count", Integer.class))
                .clusterId(metadata.getColumnNames().contains("cluster_id") ? row.get("cluster_id", Long.class) : null)
                .sensorId(metadata.getColumnNames().contains("sensor_id") ? row.get("sensor_id", Long.class) : null)
                .type(metadata.getColumnNames().contains("type") ? row.get("type", String.class) : null)
                .value(row.get("aggregate_result", BigDecimal.class))
                .function(getType())
                .build();

    }
}
