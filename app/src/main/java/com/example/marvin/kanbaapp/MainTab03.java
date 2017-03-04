package com.example.marvin.kanbaapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import static com.example.marvin.kanbaapp.R.id.ibAbout;
import static com.example.marvin.kanbaapp.R.id.ibLookup;


public class MainTab03 extends Fragment implements View.OnClickListener {
    View view;
    ImageButton ibCP,ibAbout,ibLookup,ibLaunch;
    Button btExit;
    //!!!!!Fragment中对于控件的初始化和操作必须在onCreatView方法中实现
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_main_tab03, container, false);
        btExit= (Button) view.findViewById(R.id.btExit);
        ibLookup= (ImageButton) view.findViewById(R.id.ibLookupCon);
        btExit.setOnClickListener(this);
        ibLookup.setOnClickListener(this);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


   public void onClick(View v) {
       switch (v.getId()) {
           case R.id.btExit:
               Intent i = getContext().getPackageManager()
                       .getLaunchIntentForPackage( getContext().getPackageName() );
               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(i);
               break;
           case R.id.ibLookupCon:
               Intent intent=new Intent(getContext(),MyCommentActivity.class);
               startActivity(intent);
               break;

       }
   }
}
