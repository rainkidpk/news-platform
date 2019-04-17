package com.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="new")
public class NewEntity extends BaseEntity{

    private static final long serialVersionUID = 3928011132604626561L;

    @Column
    private String code;

    @Column
    private String thumbnail;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String shortDescription;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;




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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
