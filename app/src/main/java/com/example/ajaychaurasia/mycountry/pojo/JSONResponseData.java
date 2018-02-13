package com.example.ajaychaurasia.mycountry.pojo;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 *
 * This will hold the main JSON Data
 */

public class JSONResponseData {
    String title;
    RowData rowData[];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RowData[] getRowData() {
        return rowData;
    }

    public void setRowData(RowData[] rowData) {
        this.rowData = rowData;
    }
}
