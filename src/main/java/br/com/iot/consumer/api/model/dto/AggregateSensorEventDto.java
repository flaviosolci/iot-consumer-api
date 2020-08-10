package br.com.iot.consumer.api.model.dto;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Value.Immutable
public interface AggregateSensorEventDto {

    BigDecimal getValue();

    Integer getCount();

    @Nullable
    String getType();

    @Nullable
    String getClusterId();

    AggregateFunctionType getFunction();
}
