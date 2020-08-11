package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.springframework.stereotype.Component;

@Component
public class MaxSqlStrategy extends BaseSqlStrategy {

    @Override
    public AggregateFunctionType getType() {
        return AggregateFunctionType.MAX;
    }

    @Override
    protected String getAggregateFunctionSql() {
        return "MAX(value)";
    }

}
