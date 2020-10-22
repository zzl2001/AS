package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String phonenumber;
    private String pwd;
    private String sex;
    private String love;
    private String staff;
    private String url;

    public User(String username, String phonenumber, String pwd, String sex, String love, String staff, String url) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.pwd = pwd;
        this.sex = sex;
        this.love = love;
        this.staff = staff;
        this.url = url;
    }



}
