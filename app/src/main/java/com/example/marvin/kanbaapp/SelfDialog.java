package com.example.marvin.kanbaapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.Bmob;

/**
 * Created by Marvin! on 2017/2/21.
 */
public class SelfDialog extends Dialog {
    Button send;
    EditText etCom;
    private String yesStr;
    public static String MY_COM;
    /**
     * 设置确定按钮和取消被点击的接口
     */
    private onYesOnclickListener yesOnclickListener;

    public SelfDialog(Context context) {
        super(context);
        Bmob.initialize(context, "5dfba717140cb2f9535e5af58de561c7");
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    MY_COM=etCom.getText().toString();
                    yesOnclickListener.onYesClick();
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        //按空白处不能取消动画
        send= (Button) findViewById(R.id.btSendCom);
        etCom= (EditText) findViewById(R.id.etCom);
        initEvent();
    }

}