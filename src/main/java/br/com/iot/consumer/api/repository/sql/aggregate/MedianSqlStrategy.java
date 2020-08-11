package br.com.iot.consumer.api.repository.sql.aggregate;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.springframework.stereotype.Component;

@Component
public class MedianSqlStrategy extends BaseSqlStrategy {

    @Override
    public AggregateFunctionType getType() {
        return AggregateFunctionType.MEDIAN;
    }

    @Override
    protected String getAggregateFunctionSql() {
        return "percentile_cont(0.5) WITHIN GROUP (ORDER BY value)";
    }

}
