package org.dzendzula.dbviewer.controller.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;

public class DataPreviewDto {

    @ApiModelProperty(value = "List of column names presented in a table")
    private List<String> columns;
    @ApiModelProperty(value = "Data preview. Format ")
    private List<HashMap<String, String>> data;
    @ApiModelProperty(value = "Number of rows showed.")
    private int rowCount;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

}
