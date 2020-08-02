package br.com.iot.consumer.api.exception;

import br.com.iot.consumer.api.model.exception.BaseErrorMessages;

public class BaseException extends Exception {

    private BaseErrorMessages baseErrorMessages;

    public BaseException(BaseErrorMessages baseErrorMessages) {
        super(baseErrorMessages.getMessage());
        this.baseErrorMessages = baseErrorMessages;
    }

    public BaseException(BaseErrorMessages baseErrorMessages, Throwable cause) {
        super(baseErrorMessages.getMessage(), cause);
        this.baseErrorMessages = baseErrorMessages;
    }

    public BaseException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_ERROR.getMessage(), cause);
    }

    public BaseErrorMessages getBaseErrorMessages() {
        return baseErrorMessages;
    }
}
