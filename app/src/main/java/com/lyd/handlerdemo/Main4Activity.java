package com.lyd.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main4Activity";
    private Button btSend;
    private Button btStop;

    //创建主线程Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //给子线程发送消息
            Message message = new Message();
            Log.d(TAG, "Main Handler");
            threadHandler.sendMessageDelayed(message, 1000);
        }
    };
    //创建子线程Handler
    private Handler threadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
        initListener();
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //给主线程发送消息
                Message message = new Message();
                message.what=1;//把what设置为1
                Log.d(TAG, "Thread Handler");
                handler.sendMessageDelayed(message, 1000);
            }
        };
    }

    private void initView() {
        btSend = (Button) findViewById(R.id.bt_send);
        btStop = (Button) findViewById(R.id.bt_stop);
    }

    private void initListener() {
        btSend.setOnClickListener(this);
        btStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                handler.sendEmptyMessage(1);
                break;
            case R.id.bt_stop:
                handler.removeMessages(1);
                break;
        }
    }
}
