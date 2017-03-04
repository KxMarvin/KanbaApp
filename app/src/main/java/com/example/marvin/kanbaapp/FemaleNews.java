package com.example.marvin.kanbaapp;

import android.net.Uri;

import java.io.Serializable;

import static android.R.attr.resource;

/**
 * Created by Marvin! on 2017/2/14.
 */

public class FemaleNews implements Serializable,Comparable {
    private String resourceUri;
    private String title,content;
    private String objectId;
    private Integer position;
    public FemaleNews(String title,String content,String resourceUri,String objectId,Integer position){
        this.content=content;
        this.title=title;
        this.objectId=objectId;
        this.resourceUri=resourceUri;
        this.position=position;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    public String getObjectId() {
        return objectId;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public int compareTo(Object o) {
        FemaleNews a= (FemaleNews) o;
        return ( this.position< a.getPosition() ? -1 : ( this.getPosition() == a.getPosition() ? 0 : 1));
    }
}
