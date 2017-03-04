package com.example.marvin.kanbaapp;

/**
 * Created by Marvin! on 2017/2/24.
 */

public class MyComment {
    private String comment;
    private String content;
    private String title;
    private String uri;
    public MyComment(String comment,String title,String content,String  uri){
        this.content=content;
        this.title=title;
        this.comment=comment;
        this.uri=uri;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        uri = uri;
    }

}
