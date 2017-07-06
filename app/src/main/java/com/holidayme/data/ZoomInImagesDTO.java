package com.holidayme.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shaikh.salim on 3/28/2016.
 */
public class ZoomInImagesDTO implements Serializable{
    private String Url;
    private String ThumbnailUrl;
    private boolean IsActive;
    private String Caption;
    private long ImageId;

    private int Category;

    private String ImageCategory;
    private ArrayList<String> Tags;


    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getThumbnailUrl() {
        return ThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        ThumbnailUrl = thumbnailUrl;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        IsActive = isActive;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public long getImageId() {
        return ImageId;
    }

    public void setImageId(long imageId) {
        ImageId = imageId;
    }

    public String getImageCategory() {
        return ImageCategory;
    }

    public void setImageCategory(String imageCategory) {
        ImageCategory = imageCategory;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }

}
