package br.com.iot.consumer.api.repository;

import br.com.iot.consumer.api.controller.request.EventsFilter;
import br.com.iot.consumer.api.repository.sql.AggregateSqlFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.Criteria;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests for  {@link SensorEventRepository} without accessing tables */
class SensorEventRepositorySimpleTest {

    private SensorEventRepository testClass;

    @BeforeEach
    void setUp() {
        testClass = new SensorEventRepository(Mockito.mock(DatabaseClient.class), Mockito.mock(AggregateSqlFactory.class));
    }

    @Test
    void testGetFindAllCriteriaOnlyTimestamp() {
        EventsFilter.Filter filter = new EventsFilter.Filter();
        filter.setStartDate(LocalDateTime.of(2020, 10, 10, 12, 12));
        filter.setEndDate(LocalDateTime.of(2020, 10, 12, 12, 12));
        final Criteria actual = testClass.getFindAllCriteria(filter);
        assertEquals("timestamp BETWEEN 2020-10-10T12:12 AND 2020-10-12T12:12", actual.toString());
    }

    @Test
    void testGetFindAllCriteriaSensorId() {
        EventsFilter.Filter filter = new EventsFilter.Filter();
        filter.setStartDate(LocalDateTime.of(2020, 10, 10, 12, 12));
        filter.setEndDate(LocalDateTime.of(2020, 10, 12, 12, 12));
        filter.setSensorId(12345L);
        final Criteria actual = testClass.getFindAllCriteria(filter);
        assertEquals("timestamp BETWEEN 2020-10-10T12:12 AND 2020-10-12T12:12 AND sensor_id = 12345", actual.toString());
    }

    @Test
    void testGetFindAllCriteriaClusterId() {
        EventsFilter.Filter filter = new EventsFilter.Filter();
        filter.setStartDate(LocalDateTime.of(2020, 10, 10, 12, 12));
        filter.setEndDate(LocalDateTime.of(2020, 10, 12, 12, 12));
        filter.setClusterId(12345L);
        final Criteria actual = testClass.getFindAllCriteria(filter);
        assertEquals("timestamp BETWEEN 2020-10-10T12:12 AND 2020-10-12T12:12 AND cluster_id = 12345", actual.toString());
    }

    @Test
    void testGetFindAllCriteriaType() {
        EventsFilter.Filter filter = new EventsFilter.Filter();
        filter.setStartDate(LocalDateTime.of(2020, 10, 10, 12, 12));
        filter.setEndDate(LocalDateTime.of(2020, 10, 12, 12, 12));
        filter.setEventType("temperature");
        final Criteria actual = testClass.getFindAllCriteria(filter);
        assertEquals("timestamp BETWEEN 2020-10-10T12:12 AND 2020-10-12T12:12 AND type = 'temperature'", actual.toString());
    }

    @Test
    void testGetFindAllCriteriaALL() {
        EventsFilter.Filter filter = new EventsFilter.Filter();
        filter.setStartDate(LocalDateTime.of(2020, 10, 10, 12, 12));
        filter.setEndDate(LocalDateTime.of(2020, 10, 12, 12, 12));
        filter.setEventType("temperature");
        filter.setClusterId(12345L);
        filter.setSensorId(12345L);
        final Criteria actual = testClass.getFindAllCriteria(filter);
        assertEquals("timestamp BETWEEN 2020-10-10T12:12 AND 2020-10-12T12:12 " +
                "AND type = 'temperature' " +
                "AND sensor_id = 12345 " +
                "AND cluster_id = 12345", actual.toString());
    }
}