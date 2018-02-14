package com.example.ajaychaurasia.mycountry.pojo;

/**
 * This will hold the main JSON Data
 */

public class JSONResponseData {
    private String title;

    public RowData[] getRows() {
        return rows;
    }

    private RowData rows[];

    public String getTitle() {
        return title;
    }
}
