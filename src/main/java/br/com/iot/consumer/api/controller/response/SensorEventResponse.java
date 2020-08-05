package br.com.iot.consumer.api.controller.response;

import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value.Immutable
public interface SensorEventResponse {

    OffsetDateTime getTimestamp();

    Long getSensorId();

    String getType();

    BigDecimal getValue();

    String getName();
}
