package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marvin! on 2017/2/15.
 */
/**
 * Activity管理类
 */
public class SysApplication extends Application {
    private List<Activity> mlist=new LinkedList();
    private static SysApplication instance;
    private SysApplication(){

    }
    public synchronized static SysApplication getInstance(){
        if (null==instance){
            instance=new SysApplication();
        }
        return instance;
    }
    //add Activity
    public void addActivity(Activity activity){
        mlist.add(activity);
    }
    public void exit(){
        try {
            for (Activity activity :mlist){
                if (activity !=null)
                    activity.finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
