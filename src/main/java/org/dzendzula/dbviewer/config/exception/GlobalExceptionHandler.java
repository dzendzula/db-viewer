package org.dzendzula.dbviewer.config.exception;

import org.dzendzula.dbviewer.controller.exceptions.BadRequestValidationException;
import org.dzendzula.dbviewer.controller.exceptions.ConnectionValidationException;
import org.dzendzula.dbviewer.controller.exceptions.NotFoundValidationException;
import org.dzendzula.dbviewer.controller.exceptions.SQLRequestException;
import org.dzendzula.dbviewer.service.impl.DbConnectionSettingsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(DbConnectionSettingsServiceImpl.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundValidationException.class)
    @ResponseBody
    public ValidationError notFoundValidationException(NotFoundValidationException ex) {
        log.warn("Validation exception: " + ex);
        MessageSourceResolvable error = ex.getError();
        if (error != null) {
            return ValidationErrorBuilder.fromMessage(error);
        } else {
            return ValidationErrorBuilder.fromError(new ObjectError(ex.getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestValidationException.class)
    @ResponseBody
    public ValidationError badRequestValidationException(BadRequestValidationException ex) {
        log.warn("Validation exception: " + ex);
        ObjectError error = ex.getError();
        if (error != null) {
            return ValidationErrorBuilder.fromError(error);
        } else {
            return ValidationErrorBuilder.fromError(new ObjectError(ex.getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @ResponseStatus(SERVICE_UNAVAILABLE)
    @ExceptionHandler(ConnectionValidationException.class)
    @ResponseBody
    public ValidationError badRequestValidationException(ConnectionValidationException ex) {
        log.warn("Validation exception: " + ex);
        MessageSourceResolvable error = ex.getError();
        if (error != null) {
            return ValidationErrorBuilder.fromMessage(error);
        } else {
            return ValidationErrorBuilder.fromError(new ObjectError(ex.getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLRequestException.class)
    @ResponseBody
    public ValidationError badRequestValidationException(SQLRequestException ex) {
        log.warn("Validation exception: " + ex);
        MessageSourceResolvable error = ex.getError();
        if (error != null) {
            return ValidationErrorBuilder.fromMessage(error);
        } else {
            return ValidationErrorBuilder.fromError(new ObjectError(ex.getClass().getSimpleName(), ex.getMessage()));
        }
    }


}
