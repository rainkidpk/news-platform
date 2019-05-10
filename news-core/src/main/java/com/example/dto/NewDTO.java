package com.example.dto;

import java.util.Map;

public class NewDTO extends AbstractDTO<NewDTO>{

    private static final long serialVersionUID = 1284356519231472812L;

    private String code;
    private String thumbnail;
    private String title;
    private String content;
    private String shortDescription;
    private Map<String, String> categories;
    private String categoryCode;
    private String imageName;

    public String getThumbnailBase64() {
        if( thumbnailBase64 == null){
            return thumbnailBase64;
        }
        return thumbnailBase64.split(",")[1];
    }

    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }

    private String thumbnailBase64;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
