package com.example.marvin.kanbaapp;

/**
 * Created by Marvin! on 2016/12/22.
 */

public class Info {
    String nicheng;
    String mima;
    int id;
    int touxiangId;
    public Info(String mima,String nicheng){
        this.nicheng=nicheng;
        this.mima=mima;
    }
    public int getId(){ return id;}
    public int getTouxiangId(){ return touxiangId;}
    public void setId(int id){this.id=id;}
    public void setTouxiangId(int touxiangId){this.touxiangId=touxiangId;}
    public String getNicheng() {
        return nicheng;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }
}
