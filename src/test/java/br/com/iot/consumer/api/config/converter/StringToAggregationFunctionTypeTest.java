package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StringToAggregationFunctionTypeTest {

    private StringToAggregationFunctionType testClass;

    @BeforeEach
    void setUp() {
        testClass = new StringToAggregationFunctionType();
    }

    private static Stream<String> enumCaseSensitive() {
        return Stream.concat(
                Stream.of(AggregateFunctionType.values()).map(AggregateFunctionType::name),
                Stream.of(AggregateFunctionType.values()).map(AggregateFunctionType::name).map(String::toLowerCase)
        );
    }

    @MethodSource("enumCaseSensitive")
    @ParameterizedTest(name = "{index} ==> Checking ''{0}''")
    void testConvert(String value) {
        assertNotNull(testClass.convert(value));
    }

}