package org.dzendzula.dbviewer.controller.vo;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class DbConnectionSettingsDto implements Serializable {

    @ApiModelProperty(value = "ID of DB connection setting.")
    private Long id;
    @NotEmpty
    @ApiModelProperty(value = "Connection settings name")
    private String name;
    @NotEmpty
    @ApiModelProperty(value = "Database host server address")
    private String host;
    @NotNull
    @ApiModelProperty(value = "DB port")
    private Integer port;
    @NotEmpty
    @ApiModelProperty(value = "Database name")
    private String dbName;
    @NotEmpty
    @ApiModelProperty(value = "DB connection username")
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "DB connection password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}