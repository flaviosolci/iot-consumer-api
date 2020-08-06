package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.controller.SensorController;
import br.com.iot.consumer.api.controller.request.AggregateSensorEventsRequest;
import br.com.iot.consumer.api.controller.request.SearchSensorEventsRequest;
import br.com.iot.consumer.api.exception.InvalidValueException;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.repository.sql.AggregateSqlFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
public class SensorEventRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SensorEventRepository.class);

    private final DatabaseClient databaseClient;
    private final AggregateSqlFactory aggregateSqlFactory;

    public SensorEventRepository(DatabaseClient databaseClient, AggregateSqlFactory aggregateSqlFactory) {
        this.databaseClient = databaseClient;
        this.aggregateSqlFactory = aggregateSqlFactory;
    }

    // ===========================
    // == SAVE
    // ===========================

    @Transactional
    public Mono<Void> save(SensorEventEntity sensorEventEntity) {
        return databaseClient.insert()
                .into(SensorEventEntity.class)
                .using(sensorEventEntity)
                .then();
    }

    // ===========================
    // == FIND ALL
    // ===========================

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Flux<AggregateSensorEventDto> aggregateAsPerRequest(AggregateSensorEventsRequest request) throws InvalidValueException {
        return aggregateSqlFactory.get(request.getFunctionType()).execute(databaseClient, request)
                .doFirst(() -> LOG.debug("==== Running query to aggregate events. Request -> {}", request));
    }
}
