package org.dzendzula.dbviewer.controller.exceptions;

import org.springframework.context.MessageSourceResolvable;

public abstract class AbstractException extends RuntimeException {

    public abstract MessageSourceResolvable getError();

    protected AbstractException(String message) {
        super(message);
    }

}
