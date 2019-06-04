package org.dzendzula.restfilterindex.converter;

import org.springframework.core.convert.converter.Converter;

public interface ConverterReversable<S, T> extends Converter<S, T> {

    S convertReverse(T target);
}
