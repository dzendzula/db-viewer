package org.dzendzula.dbviewer.controller.dto;

public class DbTableStatisticsDto {

    private String name;
    private Integer numberOfRecords;
    private Integer numberOfAttributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Integer getNumberOfAttributes() {
        return numberOfAttributes;
    }

    public void setNumberOfAttributes(Integer numberOfAttributes) {
        this.numberOfAttributes = numberOfAttributes;
    }
}
