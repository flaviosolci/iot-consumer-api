package br.com.iot.consumer.api.model.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;


public class BaseErrorMessages {

    public static final BaseErrorMessages GENERIC_ERROR = new BaseErrorMessages("generic");
    public static final BaseErrorMessages GENERIC_INVALID_PARAMETERS = new BaseErrorMessages("generic.invalid.parameter");
    public static final BaseErrorMessages GENERIC_NOT_FOUND = new BaseErrorMessages("generic.notFound");
    public static final BaseErrorMessages GENERIC_METHOD_NOT_ALLOWED = new BaseErrorMessages("generic.methodNotAllowed");
    public static final BaseErrorMessages GENERIC_UNAUTHENTICATED_EXCEPTION = new BaseErrorMessages("generic.unauthenticated");
    public static final BaseErrorMessages GENERIC_UNAUTHORIZED_EXCEPTION = new BaseErrorMessages("generic.unauthorized");

    private final String key;
    private Object[] params;

    public BaseErrorMessages(String key) {
        this.key = key;
    }

    public BaseErrorMessages withParams(Object... params) {
        this.params = params;
        return this;
    }

    public String getMessage() {
        String message = ResourceBundle.getBundle("messages.ErrorResource").getString(key);
        if (params != null) {
            final MessageFormat fmt = new MessageFormat(message);
            message = fmt.format(params);
        }
        return message;
    }
}
