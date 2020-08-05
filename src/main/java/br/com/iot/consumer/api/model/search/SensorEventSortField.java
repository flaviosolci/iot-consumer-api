package br.com.iot.consumer.api.model.search;

public enum SensorEventSortField {
    TIMESTAMP("timestamp"), ID("id"), VALUE("value"), NAME("name");

    private final String value;

    SensorEventSortField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
