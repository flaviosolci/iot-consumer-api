package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class InvalidValueException extends BaseException {

    public InvalidValueException(BaseErrorMessages baseErrorMessages) {
        super(baseErrorMessages);
    }
}
