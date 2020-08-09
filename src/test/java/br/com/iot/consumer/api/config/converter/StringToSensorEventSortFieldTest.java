package br.com.iot.consumer.api.config.converter;

import br.com.iot.consumer.api.model.search.SensorEventSortField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/** Tests for {@link StringToSensorEventSortField} */
class StringToSensorEventSortFieldTest {

    private StringToSensorEventSortField testClass;

    @BeforeEach
    void setUp() {
        testClass = new StringToSensorEventSortField();
    }

    private static Stream<String> enumCaseSensitive() {
        return Stream.concat(
                Stream.of(SensorEventSortField.values()).map(SensorEventSortField::name),
                Stream.of(SensorEventSortField.values()).map(SensorEventSortField::name).map(String::toLowerCase)
        );
    }

    @MethodSource("enumCaseSensitive")
    @ParameterizedTest(name = "{index} ==> Checking ''{0}''")
    void testConvert(String value) {
        assertNotNull(testClass.convert(value));
    }

}