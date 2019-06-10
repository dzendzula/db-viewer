package org.dzendzula.dbviewer.controller.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.dzendzula.dbviewer.domain.IdentifiedBo;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;


public class NotFoundValidationException extends AbstractException {

    private final Class<? extends IdentifiedBo> entity;

    private final MessageSourceResolvable error;


    private NotFoundValidationException(Class<? extends IdentifiedBo> entity, MessageSourceResolvable error) {
        super(error.getDefaultMessage());
        this.entity = entity;
        this.error = error;
    }

    public NotFoundValidationException(Class<? extends IdentifiedBo> entity, String id) {
        this(entity, createError(entity, id));
    }

    public NotFoundValidationException(Class<? extends IdentifiedBo> entity, String path, Long param) {
        this(entity, createError(entity, path, param));
    }

    public NotFoundValidationException(Class<? extends IdentifiedBo> entity, String path, String param) {
        this(entity, createError(entity, path, param));
    }

    private static MessageSourceResolvable createError(Class<? extends IdentifiedBo> entity, String id) {
        String name = entity.getSimpleName();
        if (name.endsWith("Bo")) {
            name = name.substring(0, name.length() - 2);
        }
        String message = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " ") + " not found [id=" + id + "]";
        return new DefaultMessageSourceResolvable(new String[]{"validation." + name + ".notFound"}, new Object[]{entity, id}, message);
    }

    private static MessageSourceResolvable createError(Class<? extends IdentifiedBo> entity, String path, Object param) {
        String name = entity.getSimpleName();
        if (name.endsWith("Bo")) {
            name = name.substring(0, name.length() - 2);
        }
        String message = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " ") + " not found [" + path + "=" + param + "]";
        return new DefaultMessageSourceResolvable(new String[]{"validation." + name + ".notFound.path"}, new Object[]{entity, path, param}, message);
    }

    @Override
    public MessageSourceResolvable getError() {
        return error;
    }
}
