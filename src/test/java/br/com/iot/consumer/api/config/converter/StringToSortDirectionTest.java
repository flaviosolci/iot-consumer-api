package br.com.iot.consumer.api.config.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/** Tests for {@link StringToSortDirection} */
class StringToSortDirectionTest {

    private StringToSortDirection testClass;

    @BeforeEach
    void setUp() {
        testClass = new StringToSortDirection();
    }

    private static Stream<String> enumCaseSensitive() {
        return Stream.concat(
                Stream.of(Sort.Direction.values()).map(Sort.Direction::name),
                Stream.of(Sort.Direction.values()).map(Sort.Direction::name).map(String::toLowerCase)
        );
    }

    @MethodSource("enumCaseSensitive")
    @ParameterizedTest(name = "{index} ==> Checking ''{0}''")
    void testConvert(String value) {
        assertNotNull(testClass.convert(value));
    }

}