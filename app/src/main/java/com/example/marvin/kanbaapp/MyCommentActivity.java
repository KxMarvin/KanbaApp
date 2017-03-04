package com.example.marvin.kanbaapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marvin.kanbaapp.db.Comment;
import com.example.marvin.kanbaapp.db.MyUser;
import com.example.marvin.kanbaapp.db.Post;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;
import static cn.bmob.newim.core.BmobIMClient.getContext;

import static com.example.marvin.kanbaapp.MainTab01.newsList1;

/**
 * Created by Marvin! on 2017/2/21.
 */

public class MyCommentActivity extends Activity {
    CommentAdpater adapter;
    private List<MyComment> commentList = new ArrayList<MyComment>();
    List<Comment>clist=new ArrayList<>();
    List<Comment>userList=new ArrayList<>();
    private static String content;
    //List<String>content=new ArrayList<>();
    //List<String>title=new ArrayList<>();
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1 :
                    Toast.makeText(MyCommentActivity.this,"存在该用户",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_comment);
        SysApplication.getInstance().addActivity(this);
        //ToolBar
      //  Bmob.initialize(this, "5dfba717140cb2f9535e5af58de561c7");
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.inflateMenu(R.menu.zhuce);
        toolbar.setTitle("我的评论");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Fresco.initialize(MyCommentActivity.this);
        initNews();
        adapter = new CommentAdpater(MyCommentActivity.this, R.layout.my_comment_list, commentList);
        ListView listView= (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

    }

    private void initNews() {
        final  MyUser user = BmobUser.getCurrentUser(MyUser.class);
        //同一个用户的所有评论
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        query.order("-updatedAt");
        BmobUser user1 = BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("author", user1);    // 查询当前用户的所有微博
        //include的用法 不然无法直接得到post！！！
        query.include("post,post.title,post.content,post.picId");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){
                    clist=list;
                    for(int i=0;i<clist.size();i++) {
                        MyComment comment = new MyComment(clist.get(i).getContent(),clist.get(i).getPost().getTitle(),clist.get(i).getPost().getContent(),clist.get(i).getPost().getPicId());
                        commentList.add(comment);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(MyCommentActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        查询用户

        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.addQueryKeys("author");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if(e==null){
                    userList=object;
                    for(int i=0;i<clist.size();i++){
                      if (user.equals(userList.get(i).getAuthor())){
                          Message msg=new Message();
                          msg.what=1;
                          mHandler.sendMessage(msg);
                      }
                    }
                }else{
                    Toast.makeText(MyCommentActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }});


        //查询评论
        BmobQuery<Comment> bmobQuery1 = new BmobQuery<Comment>();
        bmobQuery1.addQueryKeys("content");
        bmobQuery1.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if(e==null){
                    clist=object;
                    for(int i=0;i<clist.size();i++){
                        //Toast.makeText(MyCommentActivity.this,clist.get(i).getContent(),Toast.LENGTH_SHORT).show();

                        MyComment comment=new MyComment((clist.get(i).getContent()),"我是标题","我是内容","");
                        commentList.add(comment);
                        adapter.notifyDataSetChanged();

                    }
                }else{
                    Toast.makeText(MyCommentActivity.this,"失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
       // MyComment comment1=new MyComment("我是评论1","我是标题1","我是内容1","");
        //MyComment comment2=new MyComment("我是评论2","我是标题2","我是内容2","");
        //MyComment comment3=new MyComment("我是评论3","我是标题3","我是内容3","");
        //commentList.add(comment1);
        //commentList.add(comment2);
        //commentList.add(comment3);
        */
    }

    private static class CommentAdpater extends ArrayAdapter {
        private int layoutId;
        List<MyComment> mList;
        Context mContext;//养成好习惯！！！在定义自定义适配器一定要先定义好上下文对象Context，并且在其构造器中赋值！！！

        public CommentAdpater(Context context, int resource, List<MyComment> objects) {
            super(context, resource, objects);
            layoutId = resource;
            this.mContext=context;
            this.mList=objects;

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            MyComment myComment = (MyComment) getItem(position);
            final View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            TextView tvComment= (TextView) view.findViewById(R.id.itcComment);
            TextView tvTitle = (TextView) view.findViewById(R.id.itcTitle);
            //小红点
            TextView tvCotent = (TextView) view.findViewById(R.id.itcContent);
            SimpleDraweeView ivNews = (SimpleDraweeView)view.findViewById(R.id.ivcPic);
            Uri uri = Uri.parse(mList.get(position).getUri());
            ivNews.setImageURI(uri);
            tvComment.setText(myComment.getComment());
            tvTitle.setText(myComment.getTitle());
            tvCotent.setText(myComment.getContent());
            ivNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,RealContent.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("imageId",mList.get(position).getUri());
                    bundle.putString("title",mList.get(position).getTitle());
                    bundle.putString("content",mList.get(position).getContent());
                    intent.putExtras(bundle);
                    //利用上下文开启跳转
                    mContext.startActivity(intent);

                }
            });
            return view;

        }
    }


}
