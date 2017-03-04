package com.example.marvin.kanbaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.notificatoinbutton.library.NotificationButton;
import com.example.marvin.kanbaapp.db.MyUser;
import com.example.marvin.kanbaapp.db.Post;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;


import static com.example.marvin.kanbaapp.FirstActivity.idList2;
import static com.example.marvin.kanbaapp.MainTab01.newsList1;


public class MainTab02 extends ListFragment {
    public List<FemaleNews> newsList2 = new ArrayList<FemaleNews>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    FemaleNews news2 = (FemaleNews) msg.getData().getSerializable("news");
                    newsList2.add(news2);
                    //利用排序解决了初始化可能顺序不对的问题，由于查询都是子线程故有快有慢 message的发送也有快有慢
                    Collections.sort(newsList2);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    View view;
    NewsAdpater adapter;
    MyUser user = BmobUser.getCurrentUser(MyUser.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_main_tab02, container, false);
        Fresco.initialize(getContext());

        initNews();
        adapter = new MainTab02.NewsAdpater(getActivity(), R.layout.news, newsList2);
        setListAdapter(adapter);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    //Fragement使用ListView的注意事项：1.继承ListFragment 2.用getActivity（）得到Context
    // 3.直接用setListAdapter(adapter)而不是listView.setAdapter() 4.在Fragment的布局中必须包含id 为”@id/android:list”的Listview
    //School
    /*private void initNews() {
        FemaleNews news1 = new FemaleNews("我是标题1啊", "我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容", "http://10.211.40.205/huge1.jpg");
        newsList.add(news1);
        FemaleNews news2 = new FemaleNews("我是标题2啊", "我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2","http://10.211.40.205/huge2.jpg");
        newsList.add(news2);
        FemaleNews news3 = new FemaleNews("我是标题3啊", "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3", "http://10.211.40.205/huge3.jpg");
        newsList.add(news3);
        FemaleNews news4=new FemaleNews("我是标题4啊", "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4","http://10.211.40.205/huge4.jpg");
        newsList.add(news4);
    }*/
    //School
    private void initNews() {
        for(int i=0;i<FirstActivity.idList2.size();i++) {
            BmobQuery<Post> bmobQuery1 = new BmobQuery<Post>();
            bmobQuery1.order("-positon");
            bmobQuery1.getObject(FirstActivity.idList2.get(i).toString(), new QueryListener<Post>() {
                @Override
                public void done(Post object, BmobException e) {
                    if (e == null) {
                        FemaleNews news1 = new FemaleNews(object.getTitle(), object.getContent(), object.getPicId(), object.getObjectId(), object.getPosition());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("news", news1);
                        Message msg = new Message();
                        msg.setData(bundle);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {

                    }
                }
            });
        }
       /* BmobQuery<Post> bmobQuery1 = new BmobQuery<Post>();
        bmobQuery1.getObject("qbNV7779", new  QueryListener<Post>() {
            @Override
            public void done(Post object,BmobException e) {
                if(e==null){
                    FemaleNews news1 = new FemaleNews(object.getTitle(),object.getContent(),object.getPicId(),object.getObjectId());
                    newsList2.add(news1);
                    adapter.notifyDataSetChanged();
                }else{

                }
            }
        });
        BmobQuery<Post> bmobQuery2 = new BmobQuery<Post>();
        bmobQuery2.getObject("6M7U888A", new  QueryListener<Post>() {
            @Override
            public void done(Post object,BmobException e) {
                if(e==null){
                    FemaleNews news2 = new FemaleNews(object.getTitle(),object.getContent(),object.getPicId(),object.getObjectId());
                    newsList2.add(news2);
                    adapter.notifyDataSetChanged();

                }else{


                }
            }
        });
        BmobQuery<Post> bmobQuery3 = new BmobQuery<Post>();
        bmobQuery3.getObject("RWMY666W", new  QueryListener<Post>() {
            @Override
            public void done(Post object,BmobException e) {
                if(e==null){
                    FemaleNews news3 = new FemaleNews(object.getTitle(),object.getContent(),object.getPicId(),object.getObjectId());
                    newsList2.add(news3);
                    adapter.notifyDataSetChanged();
                }else{


                }
            }
        });
        BmobQuery<Post> bmobQuery4 = new BmobQuery<Post>();
        bmobQuery4.getObject("ZaOU111d", new  QueryListener<Post>() {
            @Override
            public void done(Post object,BmobException e) {
                if(e==null){
                    FemaleNews news4=new FemaleNews(object.getTitle(),object.getContent(),object.getPicId(),object.getObjectId());
                    newsList2.add(news4);
                    adapter.notifyDataSetChanged();
                }else{


                }
            }
        });

       /* FemaleNews news1 = new FemaleNews("我是标题1啊", "我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容", "http://10.1.1.189/huge1.jpg");
        newsList.add(news1);
        FemaleNews news4=new FemaleNews("我是标题4啊", "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4","http://10.1.1.189/huge4.jpg");
        newsList.add(news4);
        FemaleNews news3 = new FemaleNews("我是标题3啊", "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3", "http://10.1.1.189/huge3.jpg");
        newsList.add(news3);
        FemaleNews news2 = new FemaleNews("我是标题2啊", "我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2","http://10.1.1.189/huge2.jpg");
        newsList.add(news2);*/

    }
    /*
    private void initNews() {
        final Post post1 = new Post();
        post1.setTitle("我是标题1啊");
        post1.setUser(user);
        post1.setContent("我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容");
        post1.setPicId("http://10.1.1.189/pic1.jpg");
        post1.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news1 = new FemaleNews("我是标题1啊", "我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容", "http://10.1.1.189/huge1.jpg",post1.getObjectId());
                    newsList2.add(news1);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        final Post post2 = new Post();
        post2.setTitle("我是标题2啊");
        post2.setUser(user);
        post2.setContent("我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2");
        post2.setPicId("http://10.1.1.189/qm.png");
        post2.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news2 = new FemaleNews("我是标题2啊", "我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2","http://10.1.1.189/huge2.jpg",post2.getObjectId());
                    newsList2.add(news2);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        final Post post3 = new Post();
        post3.setTitle("我是标题3啊");
        post3.setUser(user);
        post3.setContent( "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3");
        post3.setPicId("http://10.1.1.189/nv4.png");
        post3.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news3 = new FemaleNews("我是标题3啊", "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3", "http://10.1.1.189/huge3.jpg",post3.getObjectId());
                    newsList2.add(news3);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        final Post post4 = new Post();
        post4.setTitle("我是标题4啊");
        post4.setUser(user);
        post4.setContent(  "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4");
        post4.setPicId("http://10.1.1.189/nv4.png");
        post4.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news4=new FemaleNews("我是标题4啊", "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4","http://10.1.1.189/huge4.jpg", post4.getObjectId());
                    newsList2.add(news4);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        }*/






    private static class NewsAdpater extends ArrayAdapter {
        private int layoutId;
        List<FemaleNews> mList;
        Context mContext;//养成好习惯！！！在定义自定义适配器一定要先定义好上下文对象Context，并且在其构造器中赋值！！！

        public NewsAdpater(Context context, int resource, List<FemaleNews> objects) {
            super(context, resource, objects);
            layoutId = resource;
            this.mContext=context;
            this.mList=objects;

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            FemaleNews femaleNews = (FemaleNews) getItem(position);
            final View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            //小红点
            TextView tvCotent = (TextView) view.findViewById(R.id.content);
            SimpleDraweeView ivNews = (SimpleDraweeView)view.findViewById(R.id.ivNews);
            Uri uri = Uri.parse(mList.get(position).getResourceUri());
            ivNews.setImageURI(uri);
            tvTitle.setText(femaleNews.getTitle());
            tvCotent.setText(femaleNews.getContent());
            ivNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,RealContent.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("sex",1);
                    bundle.putString("imageId",mList.get(position).getResourceUri());
                    bundle.putString("title",mList.get(position).getTitle());
                    bundle.putString("content",mList.get(position).getContent());
                    intent.putExtras(bundle);
                    intent.putStringArrayListExtra("idList2", idList2);
                    //利用上下文开启跳转
                    mContext.startActivity(intent);

                }
            });
            return view;

        }
    }

}
