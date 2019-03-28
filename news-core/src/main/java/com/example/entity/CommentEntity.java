package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class CommentEntity extends BaseEntity{

    private static final long serialVersionUID = 1212647842240313909L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
