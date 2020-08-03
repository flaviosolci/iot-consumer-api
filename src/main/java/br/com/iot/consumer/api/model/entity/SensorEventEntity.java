package br.com.iot.consumer.api.model.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Table("sensor_event")
public class SensorEventEntity {

    private OffsetDateTime timestamp;
    private Long sensorId;
    protected String type;
    protected BigDecimal value;

    public SensorEventEntity(OffsetDateTime timestamp, Long sensorId, String type, BigDecimal value) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.type = type;
        this.value = value;
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

    @Override
    public String toString() {
        return "SensorEventEntity{" +
                "timestamp=" + timestamp +
                ", sensorId=" + sensorId +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
