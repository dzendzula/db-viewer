package org.dzendzula.dbviewer.controller.exceptions;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;


public class SQLRequestException extends AbstractException {


    private MessageSourceResolvable error;


    private SQLRequestException(String requestType, MessageSourceResolvable error) {
        super(error.getDefaultMessage());
        this.error = error;
    }

    public SQLRequestException(String requestType, String id) {
        this(requestType, createError(requestType, id));
    }

    private static MessageSourceResolvable createError(String requestType, String id) {
        String message = "SQL reqest of type " + requestType + " to database with ID: " + id + " failed.";
        return new DefaultMessageSourceResolvable(new String[]{"validation." + requestType + ".failed"}, new Object[]{requestType, id}, message);
    }

    @Override
    public MessageSourceResolvable getError() {
        return error;
    }
}
