package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.SensorEventGroupBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/** Tests for {@link StringToSensorEventGroupBy} */
class StringToSensorEventGroupByTest {

    private StringToSensorEventGroupBy testClass;

    @BeforeEach
    void setUp() {
        testClass = new StringToSensorEventGroupBy();
    }

    private static Stream<String> enumCaseSensitive() {
        return Stream.concat(
                Stream.of(SensorEventGroupBy.values()).map(SensorEventGroupBy::name),
                Stream.of(SensorEventGroupBy.values()).map(SensorEventGroupBy::name).map(String::toLowerCase)
        );
    }

    @MethodSource("enumCaseSensitive")
    @ParameterizedTest(name = "{index} ==> Checking ''{0}''")
    void testConvert(String value) {
        assertNotNull(testClass.convert(value));
    }
}