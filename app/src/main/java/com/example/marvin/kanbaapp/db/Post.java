package com.example.marvin.kanbaapp.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin! on 2017/2/24.
 */

public class Post extends BmobObject {
    private String title;
    private String content;
    private String picId;
    private MyUser user;
    private Integer position;
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

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Integer getPosition() {
        return position;
    }
}
