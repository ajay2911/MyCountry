package com.example.ajaychaurasia.mycountry.pojo;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 * <p>
 * This holds each of the Row Data in the Response
 */

public class RowData {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageHref() {
        return imageHref;
    }

    private String imageHref;
}
