package com.example.marvin.kanbaapp;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kanbaapp.db.Comment;
import com.example.marvin.kanbaapp.db.Post;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.R.id.list;
import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static com.example.marvin.kanbaapp.RealContent.infoList;
import static com.example.marvin.kanbaapp.RealContent.postPosition;

/**
 * Created by Marvin! on 2017/2/21.
 */

public class CommentActivity extends Activity {
    TextView textView2;
    int postPosition1;
    ArrayAdapter<String> adapter;
    ListView listView;
    String content;
    ArrayList<String>list=new ArrayList<>();
    ArrayList<String>contentList=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter=new ArrayAdapter<String>(CommentActivity.this,android.R.layout.simple_expandable_list_item_1,msg.getData().getStringArrayList("content"));
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    textView2.setText("NO CONMMENT!");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_page);
        SysApplication.getInstance().addActivity(this);
        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.inflateMenu(R.menu.zhuce);

        toolbar.setTitle("查看评论");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView= (ListView) findViewById(R.id.myListView);

        Log.d("t","onCreate");
    }


    private void inits() {
        postPosition1=getIntent().getIntExtra("position",1);
        list = getIntent().getStringArrayListExtra("infoList1");
        //bug所在的关键！！！
        if (list.size()==0){
            Log.d("t","list size is zero...");
            postPosition1=getIntent().getIntExtra("position",1);
            list = getIntent().getStringArrayListExtra("infoList1");
            return;
        }
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        Post post = new Post();
        post.setObjectId(list.get(postPosition1).toString());
        query.addWhereEqualTo("post",new BmobPointer(post));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");

        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> objects, BmobException e) {
                if (objects.size()==0){
                    Message msg=new Message();
                    msg.what=2;
                    handler.sendMessage(msg);
                }else {
                    for (int i = 0; i < objects.size(); i++) {

                       contentList.add(objects.get(i).getContent());
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("content", contentList);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("t","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("t","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("t","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("t","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("t","onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        inits();
        Log.d("t","onStart");
    }
}
