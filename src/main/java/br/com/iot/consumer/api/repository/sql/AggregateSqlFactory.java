package br.com.iot.consumer.api.repository.sql;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import br.com.iot.consumer.api.repository.sql.aggregate.AggregateSqlStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AggregateSqlFactory {

    private final Map<AggregateFunctionType, AggregateSqlStrategy> availableStrategies;

    public AggregateSqlFactory(List<AggregateSqlStrategy> sqlStrategies) {
        availableStrategies = sqlStrategies.stream().collect(Collectors.toUnmodifiableMap(AggregateSqlStrategy::getType, strategy -> strategy));
    }

    public AggregateSqlStrategy get(AggregateFunctionType functionType) {
        return availableStrategies.get(functionType);
    }
}
