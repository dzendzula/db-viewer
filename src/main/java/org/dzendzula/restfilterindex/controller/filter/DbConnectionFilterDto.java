package org.dzendzula.restfilterindex.controller.filter;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DbConnectionFilterDto implements Serializable {

    private String name;
    private String host;
    private String dbName;

}
