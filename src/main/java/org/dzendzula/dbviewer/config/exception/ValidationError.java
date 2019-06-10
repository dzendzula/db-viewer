package org.dzendzula.dbviewer.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {

    // --- fields ---

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MessageSourceResolvable> errors;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ObjectError> objects;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> fields;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String errorMessage;

    // --- constructor ---

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationError(String errorMessage, MessageSourceResolvable error) {
        this.errorMessage = errorMessage;
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public ValidationError(String errorMessage, List<MessageSourceResolvable> errors) {
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public ValidationError(String errorMessage, List<ObjectError> objects, List<FieldError> fields) {
        this.errorMessage = errorMessage;
        this.objects = objects;
        this.fields = fields;
    }


}
