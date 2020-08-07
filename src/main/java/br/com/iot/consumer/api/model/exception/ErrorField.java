package br.com.iot.consumer.api.model.exception;

import org.immutables.value.Value;

@Value.Immutable
public interface ErrorField {
    String getField();

    String getDescription();
}
