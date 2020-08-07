package br.com.iot.consumer.api.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableSensorEvent.Builder.class)
public interface SensorEvent {
    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();
}
