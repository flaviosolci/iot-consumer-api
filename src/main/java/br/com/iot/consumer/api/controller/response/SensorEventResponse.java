package br.com.iot.consumer.api.controller.response;

import br.com.iot.consumer.api.model.event.ImmutableSensorEvent;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value.Immutable
@JsonDeserialize(builder = ImmutableSensorEvent.Builder.class)
public interface SensorEventResponse {

    OffsetDateTime getTimestamp();

    Long getSensorId();

    String getType();

    BigDecimal getValue();

    String getName();
}
