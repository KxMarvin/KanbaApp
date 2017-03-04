package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.notificatoinbutton.library.NotificationButton;
import com.example.marvin.kanbaapp.db.Comment;
import com.example.marvin.kanbaapp.db.KanbaOpenHelper;
import com.example.marvin.kanbaapp.db.MyUser;
import com.example.marvin.kanbaapp.db.Post;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.id;
import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;

import static com.example.marvin.kanbaapp.R.id.etComment;

/**
 * Created by Marvin! on 2017/2/14.
 */

public class RealContent extends AppCompatActivity implements View.OnClickListener{
    NotificationButton send,collect;
    EditText comment;
    public static ArrayList<FemaleNews> listObj;
    public static ArrayList<String> infoList = new ArrayList<String>();

    int colNum,comNum;
    private long exitTime = 0;
    private SelfDialog dialog;
    private KanbaOpenHelper kanbaOpenHelper;
    private SQLiteDatabase database;
    public static int sex;
    public static int postPosition;
    Post post;
    /*private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                     SelfDialog dialog = new SelfDialog(RealContent.this);
                    dialog.setContentView(R.layout.my_dialog);
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    lp.x = 600; // 新位置X坐标
                    lp.y = 500; // 新位置Y坐标
                    lp.width = 1000; // 宽度
                    lp.height = 1000; // 高度
                    lp.alpha = 1f; // 透明度
                    dialogWindow.setAttributes(lp);
                    dialog.setYesOnclickListener(new SelfDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            Toast.makeText(RealContent.this,"GOOD",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();
                    break;
            }
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.realcontent1);
        SysApplication.getInstance().addActivity(this);
        Bmob.initialize(this, "5dfba717140cb2f9535e5af58de561c7");
        //initsDataBase();
        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.inflateMenu(R.menu.content);
        toolbar.setTitle("看吧");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(RealContent.this,MainActivity2.class));
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_share) {

                }
                return true;
            }
        });
        //小红点
        send= (NotificationButton) findViewById(R.id.ibrCom);
        collect= (NotificationButton) findViewById(R.id.ibrCol);
        comNum=24;
        colNum=49;
        send.setNotificationNumber(comNum);
        collect.setNotificationNumber(colNum);

        Bundle bundle=getIntent().getExtras();
        String title=bundle.getString("title");
        String content=bundle.getString("content");
        String id=bundle.getString("imageId");
        sex=bundle.getInt("sex");
        postPosition=bundle.getInt("position");
        TextView tvTitle= (TextView) findViewById(R.id.tvRTitle);
        TextView tvContent= (TextView) findViewById(R.id.tvRContent);
        SimpleDraweeView pic= (SimpleDraweeView) findViewById(R.id.ivPic);
        tvTitle.setText(title);
        tvContent.setText(content);
        Uri uri = Uri.parse(id);
        pic.setImageURI(uri);
        //
        //评论点赞功能的实现
        comment= (EditText) findViewById(etComment);
        send.setOnClickListener(this);
        collect.setOnClickListener(this);
        comment.setOnClickListener(this);
       // Toast.makeText(RealContent.this,listObj.get(postPosition).getTitle(),Toast.LENGTH_SHORT).show();

    }

    private void initsDataBase() {
        kanbaOpenHelper=new KanbaOpenHelper(this,"CommentStore.db",null,1);
        database=kanbaOpenHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibrCom:
                //Toast.makeText(RealContent.this,"评论",Toast.LENGTH_SHORT).show();
                if(sex==0){
                infoList = getIntent().getStringArrayListExtra("idList1");
                }else{
                    infoList = getIntent().getStringArrayListExtra("idList2");
                }
                Intent intent1=new Intent(RealContent.this,CommentActivity.class);
                intent1.putStringArrayListExtra("infoList1",infoList);
                intent1.putExtra("position",postPosition);
                startActivity(intent1);
                break;
            case R.id.ibrCol:
                colNum++;
                collect.setNotificationNumber(colNum);
                break;
            case etComment:
                       // Message message=new Message();
                        //message.what=1;
                        //handler.sendMessage(message);
                dialog = new SelfDialog(RealContent.this);
                dialog.setContentView(R.layout.my_dialog);
                dialog.setTitle("快速评论");
                dialog.setYesOnclickListener(new SelfDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                       //数据库
                        //女
                        if(sex==0){
                        MyUser user = BmobUser.getCurrentUser(MyUser.class);
// 创建帖子信息
                       // Toast.makeText(RealContent.this,"女",Toast.LENGTH_SHORT).show();
                            infoList = getIntent().getStringArrayListExtra("idList1");
                        Comment comment = new Comment();
//添加一对一关联
                            Post post = new Post();
                            post.setObjectId(infoList.get(postPosition).toString());
                        comment.setContent(dialog.MY_COM.toString());
                        comment.setAuthor(user);
                        comment.setPost(post);
                        comment.save(new SaveListener<String>() {

                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Toast.makeText(RealContent.this,"评论成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RealContent.this,"评论失败:",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });}
                        //男
                        else{
                            MyUser user = BmobUser.getCurrentUser(MyUser.class);
// 创建帖子信息
                           // Toast.makeText(RealContent.this,"男",Toast.LENGTH_SHORT).show();
                            infoList = getIntent().getStringArrayListExtra("idList2");
                            Post post= new Post();
                            post.setObjectId(infoList.get(postPosition).toString());
                            Comment comment = new Comment();
//添加一对一关联
                            comment.setContent(dialog.MY_COM.toString());
                            comment.setAuthor(user);
                            comment.setPost(post);
                            comment.save(new SaveListener<String>() {

                                @Override
                                public void done(String objectId,BmobException e) {
                                    if(e==null){
                                        Toast.makeText(RealContent.this,"评论成功",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RealContent.this,"评论失败:",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        }
                        if (dialog.MY_COM.equals("")){
                            Toast.makeText(RealContent.this,"评论不能为空",Toast.LENGTH_SHORT).show();
                        }else {
                            // ContentValues values=new ContentValues();
                            //values.put("comment",dialog.MY_COM);
                            //database.insert("Comment",null,values);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
              break;
        }
    }
    //监听返回按钮操作事件
   /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(RealContent.this,MainActivity2.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/




}
