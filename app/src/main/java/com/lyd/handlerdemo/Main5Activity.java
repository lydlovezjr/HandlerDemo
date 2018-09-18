package com.lyd.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {
    private TextView tv;
    //更新UI的几种方式
    //1.runOnUiThread()
    //2.handler post
    //3.handler sendmessage
    //4.view post


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            tv.setText("OK");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("OK");
                        }
                    });
                    //                    runOnUiThread(new Runnable() {
                    //                        @Override
                    //                        public void run() {
                    //                            tv.setText("OK");
                    //                        }
                    //                    });
                    //handler.sendEmptyMessage(1);

                    //                    handler.post(new Runnable() {
                    //                        @Override
                    //                        public void run() {
                    //                            tv.setText("OK");
                    //                        }
                    //                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }


}
