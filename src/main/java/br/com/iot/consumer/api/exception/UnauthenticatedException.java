package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class UnauthenticatedException extends BaseException {

    public UnauthenticatedException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_UNAUTHENTICATED_EXCEPTION, cause);
    }
}
