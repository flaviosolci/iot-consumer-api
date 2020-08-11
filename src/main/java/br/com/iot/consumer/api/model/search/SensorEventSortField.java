package br.com.iot.consumer.api.model.search;

public enum SensorEventSortField {
    TIMESTAMP("timestamp"), SENSOR_ID("sensor_id"), CLUSTER_ID("cluster_id");

    private final String value;

    SensorEventSortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
