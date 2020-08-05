package br.com.iot.consumer.api.repository.utils;

import br.com.iot.consumer.api.exception.InvalidValueException;
import br.com.iot.consumer.api.model.exception.BaseErrorMessages;
import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static br.com.iot.consumer.api.model.search.AggregateFunctionType.*;

public class AggregateUtils {
    private static final Map<AggregateFunctionType, String> sqlByFunctionType = Map.ofEntries(
            Map.entry(AVG, "AVG(value)"),
            Map.entry(MAX, "MAX(value)"),
            Map.entry(MIN, "MIN(value)"),
            Map.entry(MEDIAN, "percentile_cont(0.5) WITHIN GROUP (ORDER BY value)"));


    public static String findSqlByFunctionType(AggregateFunctionType functionType) throws InvalidValueException {
        final String query = sqlByFunctionType.get(functionType);
        if (StringUtils.isEmpty(query)) {
            throw new InvalidValueException(BaseErrorMessages.GENERIC_INVALID_PARAMETERS_WITH_VALUE.withParams(functionType));
        }

        return query;
    }
}

