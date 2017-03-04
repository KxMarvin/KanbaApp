package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kanbaapp.db.MyUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.handle;
import static android.R.attr.id;
import static android.R.attr.x;
import static android.R.id.edit;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static com.example.marvin.kanbaapp.R.id.headImage1;

/**
 * Created by Marvin! on 2016/12/21.
 */

public class ZhuceAcitivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView;
    TextView zhuce;
    EditText nicheng,mima,ensureMima;
    public  static  final int CHOOSE_PHOTO=3;
    Info info1;
    List<Info>infoList=new ArrayList<Info>();
    private long exitTime = 0;
    public Bitmap photo;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageView headImage1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        SysApplication.getInstance().addActivity(this);
        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.inflateMenu(R.menu.zhuce);
        toolbar.setTitle("注册");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ZhuceAcitivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });





        headImage1= (ImageView) findViewById(R.id.headImage1);
        nicheng= (EditText) findViewById(R.id.editNicheng);
        mima= (EditText) findViewById(R.id.editMima);
        zhuce= (TextView) findViewById(R.id.buttonZhuce);
        ensureMima= (EditText) findViewById(R.id.editEnsureMima);
        headImage1.setOnClickListener(this);
        ensureMima.setOnClickListener(this);
        nicheng.setOnClickListener(this);
        mima.setOnClickListener(this);
        zhuce.setOnClickListener(this);
        info1=new Info(nicheng.getText().toString(),mima.getText().toString());
        infoList.add(info1);
    }
    //因为此Acitivity始终在栈内  则在需在onRestart方法里销毁掉
    @Override
    protected void onDestroy() {
        SysApplication.getInstance().exit();
    }

    //监听返回按钮操作事件
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(ZhuceAcitivity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headImage1:
                choseHeadImageFromGallery();
                break;
            case R.id.editNicheng:
                break;
            case R.id.editMima:
                break;
            case R.id.editEnsureMima:

                break;
            case R.id.buttonZhuce:
                if (mima.getText().toString().equals(ensureMima.getText().toString())) {
                    MyUser p2 = new MyUser();
                    p2.setUsername(nicheng.getText().toString());
                    p2.setPassword(mima.getText().toString());
                    p2.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(ZhuceAcitivity.this, "注册成功:" + s.toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ZhuceAcitivity.this,LoginActivity.class));
                            } else {
                                Toast.makeText(ZhuceAcitivity.this, "注册失败" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
                }else {
                    Toast.makeText(ZhuceAcitivity.this,"前后输入不一致",Toast.LENGTH_SHORT).show();
                    ensureMima.setText("");
                    mima.setText("");
                }
        }
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
    //**实现调用相册

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;


            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        ContentResolver cr = this.getContentResolver();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage1.setImageBitmap(photo);
        }
    }




}
