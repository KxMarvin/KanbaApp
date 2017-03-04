package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kanbaapp.db.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.id;


/**
 * Created by Marvin! on 2017/2/24.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    TextView login,zhuce;
    TextView accountText,passwordText;
    TextView nice;
    private long exitTime = 0;
    String res;
    String password;
    String name;
    String errMsg;
    List<Info> list=new ArrayList<Info>();
    List<Info>myList;
    private int errCode;
    private  int  launchuResult;
    private final String TEXT_STRING = "注册新账号";
    private final String TEXT_KEY = "注册新账号";
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);
        Bmob.initialize(this, "5dfba717140cb2f9535e5af58de561c7");
        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.inflateMenu(R.menu.denglu);
        toolbar.setTitle("登录");
        accountText= (TextView) findViewById(R.id.editText11);
        passwordText= (TextView) findViewById(R.id.editText22);
        nice= (TextView) findViewById(R.id.btLogin11);
        zhuce= (TextView) findViewById(R.id.button2);
        zhuce.setOnClickListener(this);
        nice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                startActivity(new Intent(LoginActivity.this,ZhuceAcitivity.class));
                break;
            case R.id.btLogin11:
                BmobUser bu1 = new BmobUser();
                bu1.setUsername(accountText.getText().toString());
                bu1.setPassword(passwordText.getText().toString());
                bu1.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Toast.makeText(LoginActivity.this,"登录成功" ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity2.class));
                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
