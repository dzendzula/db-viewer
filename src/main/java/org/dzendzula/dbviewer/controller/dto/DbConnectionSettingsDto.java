package org.dzendzula.dbviewer.controller.dto;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbConnectionSettingsDto)) return false;
        DbConnectionSettingsDto that = (DbConnectionSettingsDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(host, that.host) &&
                Objects.equals(port, that.port) &&
                Objects.equals(dbName, that.dbName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, host, port, dbName, username, password);
    }
}