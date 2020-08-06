package br.com.iot.consumer.api.model.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SensorEvent {

    private Long id;
    private BigDecimal value;
    private LocalDateTime timestamp;
    private String type;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public SensorEvent setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "id=" + id +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
