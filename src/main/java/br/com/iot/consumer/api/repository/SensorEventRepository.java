package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.controller.request.AggregateEventsFilter;
import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.model.dto.AggregateSensorEventDto;
import br.com.iot.consumer.api.model.entity.SensorEventEntity;
import br.com.iot.consumer.api.repository.sql.AggregateSqlFactory;
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

import java.util.Map;

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
    public Flux<SensorEventEntity> findBySensorIdWithFilters(EventsFilter filterRequest) {
        return databaseClient.select()
                .from(SensorEventEntity.class)
                .matching(getFindAllCriteria(filterRequest.getFilter()))
                .page(getFindAllPageable(filterRequest))
                .as(SensorEventEntity.class)
                .all();
    }

    private PageRequest getFindAllPageable(EventsFilter filterRequest) {
        return PageRequest.of(filterRequest.getPage().getOffset(),
                filterRequest.getPage().getLimit(),
                Sort.by(filterRequest.getPage().getSortDirection(), filterRequest.getPage().getSortBy().getValue()));
    }

    protected Criteria getFindAllCriteria(EventsFilter.Filter filter) {
        Criteria criteria = where("timestamp")
                .between(filter.getStartDate(), filter.getEndDate());

        for (Map.Entry<String, Object> optionalEntry : filter.getOptionalFields().entrySet()) {
            criteria = criteria.and(optionalEntry.getKey()).is(optionalEntry.getValue());
        }

        return criteria;
    }

    // ===========================
    // == AGGREGATION
    // ===========================

    @Transactional(readOnly = true)
    public Flux<AggregateSensorEventDto> aggregateAsPerRequest(AggregateEventsFilter request) {
        return aggregateSqlFactory.get(request.getAggregate().getType())
                .execute(databaseClient, request)
                .doFirst(() -> LOG.debug("==== Running query to aggregate events. Request -> {}", request));
    }
}
