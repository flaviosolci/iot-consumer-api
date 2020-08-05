package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.controller.SensorController;
import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.exception.InvalidValueException;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.dto.ImmutableAggregateSensorEventDto;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static br.com.iot.consumer.api.repository.utils.AggregateUtils.findSqlByFunctionType;
import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class SensorEventRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SensorController.class);

    private final DatabaseClient databaseClient;

    public SensorEventRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    // ===========================
    // == SAVE
    // ===========================

    public Mono<Void> save(SensorEventEntity sensorEventEntity) {
        return databaseClient.insert()
                .into(SensorEventEntity.class)
                .using(sensorEventEntity)
                .then();
    }

    // ===========================
    // == FIND ALL
    // ===========================

    public Flux<SensorEventEntity> findBySensorIdWithFilters(Long sensorId, SearchSensorEventsRequest filterRequest) {
        return databaseClient.select()
                .from(SensorEventEntity.class)
                .matching(getFindAllCriteria(filterRequest, sensorId))
                .page(getFindAllPageable(filterRequest))
                .as(SensorEventEntity.class)
                .all();
    }

    private PageRequest getFindAllPageable(SearchSensorEventsRequest filterRequest) {
        return PageRequest.of(filterRequest.getPage().getPage() - 1,
                filterRequest.getPage().getLimit(),
                Sort.by(filterRequest.getPage().getDirection(), filterRequest.getPage().getSortBy().getValue()));
    }

    private Criteria getFindAllCriteria(SearchSensorEventsRequest filterRequest, Long sensorId) {
        Criteria criteria = where("timestamp")
                .between(filterRequest.getFilter().getStartDate(), filterRequest.getFilter().getEndDate())
                .and("sensor_id").is(sensorId);
        if (StringUtils.isNotEmpty(filterRequest.getFilter().getEventType())) {
            criteria = criteria.and("type")
                    .is(filterRequest.getFilter().getEventType());
        }

        return criteria;
    }

    // ===========================
    // == AGGREGATION
    // ===========================

    public Flux<AggregateSensorEventDto> aggregateAsPerRequest(AggregateSensorEventsRequest request) throws InvalidValueException {
        String sql = String.format("SELECT %s as aggregate_result, count(*), %s " +
                        "  FROM sensor_event " +
                        "  WHERE (timestamp BETWEEN :start_date AND :end_date)" +
                        "  GROUP BY %s ORDER BY %s ",
                findSqlByFunctionType(request.getFunctionType()), request.getGroupByCommaDelimited(),
                request.getGroupByCommaDelimited(), request.getGroupByCommaDelimited());

        LOG.debug("==== Prepared Query -> {}", sql);

        return databaseClient.execute(sql)
                .bind("start_date", request.getStartDate())
                .bind("end_date", request.getEndDate())
                .map((row, metadata) -> mapAggregation(row, metadata, request.getFunctionType()))
                .all();
    }

    private AggregateSensorEventDto mapAggregation(Row row, RowMetadata metadata, AggregateFunctionType functionType) {
        return ImmutableAggregateSensorEventDto.builder()
                .count(row.get("count", Integer.class))
                .name(metadata.getColumnNames().contains("name") ? row.get("name", String.class) : null)
                .type(metadata.getColumnNames().contains("type") ? row.get("type", String.class) : null)
                .value(row.get("aggregate_result", BigDecimal.class))
                .function(functionType)
                .build();

    }
}
