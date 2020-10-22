package com.example.db_demo;

import java.util.ArrayList;
import java.util.List;

//业务模型
public class Teacher {
    private String name;
    private String imageId;
    private String desc;

    //构造函数
    public Teacher(String name, String imageId, String desc) {
        this.name = name;
        this.imageId = imageId;
        this.desc = desc;
    }

    // 以下都是访问内部属性的getter和setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}