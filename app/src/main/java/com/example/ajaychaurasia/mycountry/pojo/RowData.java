package com.example.ajaychaurasia.mycountry.pojo;

/**
 * Created by Ajay Chaurasia on 13-Feb-18.
 *
 * This holds each of the Row Data in the Response
 */

public class RowData {
    String title;
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    String imageHref;
}
