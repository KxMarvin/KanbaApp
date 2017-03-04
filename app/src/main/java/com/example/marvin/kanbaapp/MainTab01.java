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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.notificatoinbutton.library.NotificationButton;
import com.example.marvin.kanbaapp.db.Comment;
import com.example.marvin.kanbaapp.db.MyUser;
import com.example.marvin.kanbaapp.db.Post;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.handle;
import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.marvin.kanbaapp.FirstActivity.idList1;

import static com.example.marvin.kanbaapp.R.id.ivNews;
import static com.example.marvin.kanbaapp.R.id.never;
import static com.example.marvin.kanbaapp.R.id.textView2;
import static com.example.marvin.kanbaapp.RealContent.listObj;


public class MainTab01 extends ListFragment {
    public static ArrayList<FemaleNews> newsList1 = new ArrayList<FemaleNews>();
    View view;
    NewsAdpater adapter;
    MyUser user = BmobUser.getCurrentUser(MyUser.class);
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    FemaleNews news2 = (FemaleNews) msg.getData().getSerializable("news");
                    newsList1.add(news2);
                    Collections.sort(newsList1);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_tab01, container, false);
        Fresco.initialize(getContext());

        initNews();
        adapter = new NewsAdpater(getActivity(), R.layout.news, newsList1);
        setListAdapter(adapter);
        Log.d("Fragment","onCreateView");
        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment","onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        //防止出现多张一样的图片
        idList1.clear();
        Log.d("Fragment","onPause");
    }
    //Fragement使用ListView的注意事项：1.继承ListFragment 2.用getActivity（）得到Context
    // 3.直接用setListAdapter(adapter)而不是listView.setAdapter() 4.在Fragment的布局中必须包含id 为”@id/android:list”的Listview
    //SCHOOL
    /*
     private void initNews() {
        FemaleNews news1 = new FemaleNews("我是标题1啊", "我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容", "http://10.1.1.189/pic1.jpg");
        newsList.add(news1);
        FemaleNews news2 = new FemaleNews("我是标题2啊", "我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2", "http://10.1.1.189/qm.png");
        newsList.add(news2);
        FemaleNews news3 = new FemaleNews("我是标题3啊", "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3", "http://10.1.1.189/gakki2.jpg");
        newsList.add(news3);
        FemaleNews news4=new FemaleNews("我是标题4啊", "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4","http://10.1.1.189/nv4.png");
        newsList.add(news4);
    }*/
    //KANBA
    /*
    private void initNews() {
        post1 = new Post();
        post1.setTitle("我是标题1啊");
        post1.setContent("我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容");
        post1.setPicId("http://10.1.1.189/pic1.jpg");
        post1.setUser(user);
        post1.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news1 = new FemaleNews("我是标题1啊", "我是内容1我是内容1我是内容1我是内容我是内容1我是内容1我是内容1我是内容", "http://10.1.1.189/pic1.jpg",post1.getObjectId());
                    newsList1.add(news1);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        post2 = new Post();
        post2.setTitle("我是标题2啊");
        post2.setContent("我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2");
        post2.setPicId("http://10.1.1.189/qm.png");
        post2.setUser(user);
        post2.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news2 = new FemaleNews("我是标题2啊", "我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2我是内容2", "http://10.1.1.189/qm.png",post2.getObjectId());
                    newsList1.add(news2);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        post3 = new Post();
        post3.setTitle("我是标题3啊");
        post3.setContent( "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3");
        post3.setPicId("http://10.1.1.189/nv4.png");
        post3.getObjectId();
        post3.setUser(user);
        post3.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news3 = new FemaleNews("我是标题3啊", "我是内容3我是内容3我是内容3内容3我是内容3我是内容3我是内容3我是内容3我是内容3", "http://10.1.1.189/gakki2.jpg",post3.getObjectId());
                    newsList1.add(news3);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });
        post4 = new Post();
        post4.setUser(user);
        post4.setTitle("我是标题4啊");
        post4.setContent(  "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4");
        post4.setPicId("http://10.1.1.189/nv4.png");
        post4.save(new SaveListener<String>() {

            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    FemaleNews news4=new FemaleNews("我是标题4啊", "我是内容4我是内容4我是内容4内容4我是内容4我是内容4我是内容4我是内容4我是内容4","http://10.1.1.189/nv4.png",post4.getObjectId());
                    newsList1.add(news4);
                    adapter.notifyDataSetChanged();
                }else{
                }
            }
        });

    }
    */

    private void initNews() {

        for (int i = 0; i < FirstActivity.idList1.size(); i++) {
            BmobQuery<Post> bmobQuery1 = new BmobQuery<Post>();
            bmobQuery1.order("-positon");
            bmobQuery1.getObject(FirstActivity.idList1.get(i).toString(), new QueryListener<Post>() {
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
                        Toast.makeText(getContext(),"失败",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }


    }


    private static class NewsAdpater extends ArrayAdapter {
        private int layoutId;
        List<FemaleNews> mList;
        Context mContext;//养成好习惯！！！在定义自定义适配器一定要先定义好上下文对象Context，并且在其构造器中赋值！！！

        public NewsAdpater(Context context, int resource, List<FemaleNews> objects) {
            super(context, resource, objects);
            layoutId = resource;
            this.mContext = context;
            this.mList = objects;

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            FemaleNews femaleNews = (FemaleNews) getItem(position);
            final View view = LayoutInflater.from(getContext()).inflate(layoutId, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            //小红点
            TextView tvCotent = (TextView) view.findViewById(R.id.content);
            SimpleDraweeView ivNews = (SimpleDraweeView) view.findViewById(R.id.ivNews);
            Uri uri = Uri.parse(mList.get(position).getResourceUri());
            ivNews.setImageURI(uri);
            tvTitle.setText(femaleNews.getTitle());
            tvCotent.setText(femaleNews.getContent());
            ivNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RealContent.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("imageId", mList.get(position).getResourceUri());
                    bundle.putString("title", mList.get(position).getTitle());
                    bundle.putInt("sex", 0);
                    bundle.putInt("position", position);
                    bundle.putString("content", mList.get(position).getContent());
                    intent.putExtras(bundle);
                    intent.putStringArrayListExtra("idList1", FirstActivity.idList1);
                    //利用上下文开启跳转

                    mContext.startActivity(intent);

                }
            });
            return view;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Fragment","onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Fragment","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragment","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Fragment","onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Fragment","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Fragment","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Fragment","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Fragment","onDetach");
    }
}

