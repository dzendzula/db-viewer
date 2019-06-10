package org.dzendzula.dbviewer.controller.exceptions;


public enum ParamSource {

    PATH("path"),
    PARAMETER("parameter");


    private final String key;

    ParamSource(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
