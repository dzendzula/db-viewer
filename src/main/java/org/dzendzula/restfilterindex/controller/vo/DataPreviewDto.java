package org.dzendzula.restfilterindex.controller.vo;

import java.util.HashMap;
import java.util.List;

public class DataPreviewDto {

    private List<String> columns;
    private List<HashMap<String, String>> data;
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
