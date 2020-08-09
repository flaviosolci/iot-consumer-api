package br.com.iot.consumer.api.model.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Table("sensor_event")
public class SensorEventEntity {

    private final OffsetDateTime timestamp;
    private final Long sensorId;
    private final String type;
    private final BigDecimal value;
    private final String name;

    public SensorEventEntity(OffsetDateTime timestamp, Long sensorId, String type, BigDecimal value, String name) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.type = type;
        this.value = value;
        this.name = name;
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
