package org.dzendzula.dbviewer.config.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorBuilder {

    public static ValidationError fromMessage(String errorMessage, MessageSourceResolvable message) {
        return new ValidationError(errorMessage, message);
    }

    public static ValidationError fromMessage(MessageSourceResolvable message) {
        return new ValidationError("Validation failed.", message);
    }


    public static ValidationError fromError(ObjectError error) {
        List<ObjectError> objects = null;
        List<FieldError> fields = null;
        if (error instanceof FieldError) {
            fields = new ArrayList<>();
            fields.add((FieldError) error);
        } else if (error != null) {
            objects = new ArrayList<>();
            objects.add((ObjectError) error);
        }
        return new ValidationError("Validation failed.", objects, fields);
    }

}
