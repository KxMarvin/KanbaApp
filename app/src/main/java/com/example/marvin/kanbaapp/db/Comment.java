package com.example.marvin.kanbaapp.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Marvin! on 2017/2/24.
 */

public class Comment extends BmobObject {
    private String content;
    private MyUser author;
    private Post post;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
