package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.dto.ImmutableAggregateSensorEventDto;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;

import java.math.BigDecimal;

public abstract class BaseSqlStrategy implements AggregateSqlStrategy {

    public AggregateSensorEventDto mapAggregation(Row row, RowMetadata metadata) {
        return ImmutableAggregateSensorEventDto.builder()
                .count(row.get("count", Integer.class))
                .clusterId(metadata.getColumnNames().contains("cluster_id") ? row.get("cluster_id", String.class) : null)
                .type(metadata.getColumnNames().contains("type") ? row.get("type", String.class) : null)
                .value(row.get("aggregate_result", BigDecimal.class))
                .function(getType())
                .build();

    }
}
