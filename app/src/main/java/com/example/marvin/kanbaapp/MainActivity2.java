package com.example.marvin.kanbaapp;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Marvin! on 2016/12/21.
 */
//注意getSupportFragmentManager()若没有定义则需将Activity改为FragmentActivity 而且 注意导入的是v4包
public class MainActivity2 extends FragmentActivity implements View.OnClickListener {
    private MainTab01 mainTab01;
    private MainTab02 mainTab02;
    private MainTab03 mainTab03;
    private ImageButton mTabBtnFemale;
    private ImageButton mTabBtnMale;
    private ImageButton mTabBtnPerson;
    private long exitTime = 0;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        SysApplication.getInstance().addActivity(this);
        initViews();

        fragmentManager=getSupportFragmentManager();
        setTabSelection(0);
        Log.d("Main","onCreate");
    }

    private void initViews(){
        mTabBtnFemale= (ImageButton) findViewById(R.id.btnFemale);
        mTabBtnMale= (ImageButton) findViewById(R.id.btnMale);
        mTabBtnPerson= (ImageButton) findViewById(R.id.btnPerson);
        mTabBtnPerson.setOnClickListener(this);
        mTabBtnMale.setOnClickListener(this);
        mTabBtnFemale.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
              {
                    case R.id.btnFemale:
                          setTabSelection(0);
                           break;
                    case R.id.btnMale:
                           setTabSelection(1);
                          break;
                    case R.id.btnPerson:
                           setTabSelection(2);
                           break;

                    default:
                            break;


              }

    }

    public void setTabSelection(int index) {
        resetBtn();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                mTabBtnFemale= (ImageButton) findViewById(R.id.btnFemale);
               mTabBtnFemale.setImageResource(R.drawable.nv1);
                if (mainTab01==null){
                    mainTab01=new MainTab01();
                    transaction.add(R.id.main2,mainTab01);

                }else {
                    transaction.show(mainTab01);
                }
                break;
            case 1:
                mTabBtnMale= (ImageButton) findViewById(R.id.btnMale);
                mTabBtnMale.setImageResource(R.drawable.nan1);
                if (mainTab02==null){
                    mainTab02=new MainTab02();
                    transaction.add(R.id.main2,mainTab02);

                }else {
                    transaction.show(mainTab02);
                }
                break;
            case 2:
                mTabBtnPerson= (ImageButton) findViewById(R.id.btnPerson);
                mTabBtnPerson.setImageResource(R.drawable.person1);
                if (mainTab03==null){
                    mainTab03=new MainTab03();
                    transaction.add(R.id.main2,mainTab03);

                }else {
                    transaction.show(mainTab03);
                }
                break;

        }
        transaction.commit();
    }



    private void hideFragment(FragmentTransaction transaction) {
        if (mainTab01!=null){
          transaction.hide(mainTab01);
        }
        if (mainTab02!=null){
            transaction.hide(mainTab02);
        }
        if (mainTab03!=null){
            transaction.hide(mainTab03);
        }
    }

    private void resetBtn() {
        mTabBtnFemale= (ImageButton) findViewById(R.id.btnFemale);
        mTabBtnFemale.setImageResource(R.drawable.nv2);
        mTabBtnMale= (ImageButton) findViewById(R.id.btnMale);
        mTabBtnMale.setImageResource(R.drawable.nan2);
        mTabBtnPerson= (ImageButton) findViewById(R.id.btnPerson);
        mTabBtnPerson.setImageResource(R.drawable.person2);
    }
    //实现按两次Back键退出的功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            SysApplication.getInstance().exit();
        }
    }
    //

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Main","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main","onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Main","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Main","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Main","onRestart");
    }
}
