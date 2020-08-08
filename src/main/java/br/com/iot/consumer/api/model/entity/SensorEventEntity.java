package br.com.iot.consumer.api.model.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;

@Table("sensor_event")
public class SensorEventEntity {

    private final OffsetDateTime timestamp;
    private final Long sensorId;
    private final String type;
    private final BigDecimal value;
    private final String name;
    private final String metadata;

    public SensorEventEntity(OffsetDateTime timestamp, Long sensorId, String type, BigDecimal value, String name, String metadata) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.type = type;
        this.value = value;
        this.name = name;
        this.metadata = metadata;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public String getName() {
        return name;
    }

    public String getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "SensorEventEntity{" +
                "timestamp=" + timestamp +
                ", sensorId=" + sensorId +
                ", type='" + type + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
