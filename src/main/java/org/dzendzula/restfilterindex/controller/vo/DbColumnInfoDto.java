package org.dzendzula.restfilterindex.controller.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

public class DbColumnInfoDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String typeName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String columnSize;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String decimalDigits;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String numPrecRadix;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String remarks;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String columnDefaultValue;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String charOctetLength;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String ordinalPosition;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String isNullable;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String isAutoincrement;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean isPrimaryKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(String numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getColumnDefaultValue() {
        return columnDefaultValue;
    }

    public void setColumnDefaultValue(String columnDefaultValue) {
        this.columnDefaultValue = columnDefaultValue;
    }

    public String getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(String charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
