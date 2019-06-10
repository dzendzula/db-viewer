package org.dzendzula.dbviewer.controller.exceptions;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class BadRequestValidationException extends AbstractException {
    private final ObjectError error;

    public ObjectError getError() {
        return error;
    }


    public BadRequestValidationException(ObjectError error) {
        super(error.getDefaultMessage());
        this.error = error;
    }

    public static BadRequestValidationException toFieldError(String message, ParamSource source, String parameter, Object rejectedValue) {
        return new BadRequestValidationException(new FieldError(source.getKey(), parameter, rejectedValue, false, null, null, message));
    }

    public static BadRequestValidationException toFieldError(String message, ParamSource source, String parameter, Object rejectedValue, String messageCode, Object... arguments) {
        return new BadRequestValidationException(new FieldError(source.getKey(), parameter, rejectedValue, false, new String[]{messageCode}, arguments, message));
    }

    public static BadRequestValidationException toFieldError(String message, String source, String field, Object rejectedValue) {
        return new BadRequestValidationException(new FieldError(source, field, rejectedValue, false, null, null, message));
    }

    public static BadRequestValidationException toFieldError(String message, String source, String field, Object rejectedValue, String messageCode, Object... arguments) {
        return new BadRequestValidationException(new FieldError(source, field, rejectedValue, false, new String[]{messageCode}, arguments, message));
    }


}
