package com.example.ajaychaurasia.mycountry.pojo;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 *
 * This will hold the main JSON Data
 */

public class JSONResponseData {
    String title;

    public RowData[] getRows() {
        return rows;
    }

    public void setRows(RowData[] rows) {
        this.rows = rows;
    }

    RowData rows[];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
