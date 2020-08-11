package br.com.iot.consumer.api.controller.response;

import br.com.iot.consumer.api.model.search.AggregateFunctionType;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Value.Immutable
public interface AggregateSensorEventResponse {

    BigDecimal getValue();

    Integer getCount();

    @Nullable
    Long getClusterId();

    @Nullable
    Long getSensorId();

    @Nullable
    String getType();

    AggregateFunctionType getFunction();
}
