package br.com.iot.consumer.api.repository.sql;

import br.com.iot.consumer.api.ApplicationStarter;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.repository.sql.aggregate.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

/** Tests for {@link AggregateSqlFactory} */
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = ApplicationStarter.class)
class AggregateSqlFactoryTest {

    @Autowired
    private AggregateSqlFactory testClass;

    private static Stream<Arguments> sqlStrategy() {
        return Stream.of(
                Arguments.of(AggregateFunctionType.AVERAGE, new AverageSqlStrategy()),
                Arguments.of(AggregateFunctionType.MAX, new MaxSqlStrategy()),
                Arguments.of(AggregateFunctionType.MIN, new MinSqlStrategy()),
                Arguments.of(AggregateFunctionType.MEDIAN, new MedianSqlStrategy())
        );
    }

    @ParameterizedTest
    @MethodSource("sqlStrategy")
    void testGet(AggregateFunctionType type, AggregateSqlStrategy expected) {
        assertFalse(!testClass.get(type).getClass().isInstance(expected));
    }

}