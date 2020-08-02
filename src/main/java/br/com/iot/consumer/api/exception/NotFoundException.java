package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        super(BaseErrorMessages.GENERIC_NOT_FOUND);
    }
}
