package org.dzendzula.dbviewer.controller.exceptions;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;


public class ConnectionValidationException extends AbstractException {

    private MessageSourceResolvable error;

    private ConnectionValidationException(MessageSourceResolvable error) {
        super(error.getDefaultMessage());
        this.error = error;
    }

    public ConnectionValidationException(String id) {
        this(createError(id));
    }

    public ConnectionValidationException(String connectionName, String id) {
        this(createError(connectionName, id));
    }

    private static MessageSourceResolvable createError(String id) {
        String message = "Connection to database with ID: " + id + " cannot be established.";
        return new DefaultMessageSourceResolvable(new String[]{"validation.connection.failed"}, new Object[]{id}, message);
    }

    private static MessageSourceResolvable createError(String connectionName, String id) {
        String message = "Connection to database with name: " + connectionName + " and ID = [" + id + "] cannot be established.";
        return new DefaultMessageSourceResolvable(new String[]{"validation.connection.failed"}, new Object[]{connectionName, id}, message);
    }

    @Override
    public MessageSourceResolvable getError() {
        return error;
    }
}
