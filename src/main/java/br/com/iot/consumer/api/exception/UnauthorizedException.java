package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_UNAUTHORIZED_EXCEPTION, cause);
    }
}
