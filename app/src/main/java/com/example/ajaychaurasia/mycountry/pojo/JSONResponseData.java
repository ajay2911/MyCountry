package com.example.ajaychaurasia.mycountry.pojo;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 * <p>
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
