package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class BaseException extends Exception {

    public BaseException(BaseErrorMessages baseErrorMessages) {
        super(baseErrorMessages.getMessage());
    }

    public BaseException(BaseErrorMessages baseErrorMessages, Throwable cause) {
        super(baseErrorMessages.getMessage(), cause);
    }
}
