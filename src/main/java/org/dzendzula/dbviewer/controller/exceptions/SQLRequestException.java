package org.dzendzula.dbviewer.controller.exceptions;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;


public class SQLRequestException extends AbstractException {


    private MessageSourceResolvable error;

    private String cause;


    private SQLRequestException(String cause, MessageSourceResolvable error) {
        super(error.getDefaultMessage());
        this.error = error;
        this.cause = cause;
    }

    public SQLRequestException(String requestType, String cause, String id) {
        this(requestType, createError(requestType, cause, id));
    }

    private static MessageSourceResolvable createError(String requestType, String cause, String id) {
        String message = "SQL reqest of type " + requestType + " to database with ID: " + id + " failed with cause:" + cause;
        return new DefaultMessageSourceResolvable(new String[]{"validation." + requestType + ".failed"}, new Object[]{requestType, id}, message);
    }

    @Override
    public MessageSourceResolvable getError() {
        return error;
    }
}
