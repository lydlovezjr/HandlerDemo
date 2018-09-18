package com.lyd.handlerdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "UI当前线程:" + Thread.currentThread());
        }
    };


    class MyThread extends Thread {
        Handler handler;//与线程相关的handler的创建
        private Looper looper;//

        @Override
        public void run() {
            Looper.prepare();
            looper = Looper.myLooper();//
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.d(TAG, "当前线程:" + Thread.currentThread());
                }
            };
            Looper.loop();
        }
    }

    private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = new TextView(this);
        textView.setText("Hello Handler");
        setContentView(textView);

        myThread = new MyThread();
        myThread.start();

        //        try {
        //            Thread.sleep(1000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        myThread.handler.sendEmptyMessage(1);
        //        handler.sendEmptyMessage(1);

        //多线程并发
        handler = new Handler(myThread.looper) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "msg:" + msg);
            }
        };
        handler.sendEmptyMessage(1);
    }
}
