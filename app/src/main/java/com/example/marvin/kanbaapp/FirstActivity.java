package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.delay;


/**
 * Created by Marvin! on 2016/12/21.
 */

public class FirstActivity extends Activity  {
    public static ArrayList<String> idList1=new ArrayList<>();
    public static ArrayList<String>idList2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        SysApplication.getInstance().addActivity(this);
        initId();
        TimerTask task = new TimerTask(){

            public void run(){

                //execute the task
                Intent intent=new Intent(FirstActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        };

        Timer timer = new Timer();

        timer.schedule(task, 3000);
    }
    private void initId() {
        idList1.add("9d319ed67d");
        idList1.add("8268c2a597");
        idList1.add("c354c99027");
        idList1.add("b1519a4ac0");

        idList2.add("qbNV7779");
        idList2.add("6M7U888A");
        idList2.add("RWMY666W");
        idList2.add("ZaOU111d");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }


}
