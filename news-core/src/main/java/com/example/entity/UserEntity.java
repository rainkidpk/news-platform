package com.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity{

    private static final long serialVersionUID = 8247222079786886178L;

    @Column
    private String userName;

    @Column
    private String passWord;

    @Column
    private String fullName;

    @Column
    private int status;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
