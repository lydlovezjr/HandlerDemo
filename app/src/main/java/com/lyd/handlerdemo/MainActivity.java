package com.lyd.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //0.Handler是什么?
    //Handler是Android给我们提供用来更新UI的一套机制,也是一套消息处理机制.
    //我们可以发送消息,可以处理消息!

    //1.为什么要用Handler?
    //Android在设计的时候,封装了一套消息创建,传递,处理机制,如果不遵循这样的机制,就没有办法更新UI,并抛出异常信息!!!

    //2.Handler怎么用?

    //3.Android为什么设计只能通过Handler机制更新UI?
    //最根本目的解决多线程并发问题

    //4.Handler原理是什么?

    //Handler:负责发送消息和处理消息
    //Looper:负责接收Handler发送的消息,并直接把消息回传给Handler自己!
    //Looper:负责读取MessageQueen中的消息，读到消息之后就把消息交给Handler去处理!
    //Message:Handler接收和处理的消息对象
    //MessageQueen:存储消息对象的队列

    //5.使用Handler遇到的问题?
    //6.如何实现一个与线程相关的Handler?
    //7.HandlerThread是什么？
    //8.如何在主线程给子线程发送消息?
    //9.Android中更新UI的几种方式
    //10.非UI线程真的不能更新UI?
    private TextView tv;
    private ImageView img;
    private Button bt;

    private Handler handler = new Handler();

    private Handler handlerSendMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //tv.setText("" + msg.arg1 + "-" + msg.arg2);
            tv.setText("" + msg.obj);
        }
    };

    private int imgs[] = {R.drawable.img0, R.drawable.img1, R.drawable.img2};
    private int index;

    private MyRunnable myRunnable = new MyRunnable();


    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index % 3;
            img.setImageResource(imgs[index]);
            handler.postDelayed(myRunnable, 1000);
        }
    }

    class Person {
        public int age;
        public String name;

        @Override
        public String toString() {
            return "name==" + name + " age==" + age;
        }
    }


    private Handler handlerCallBack = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            // return false;//不拦截
            return true;//拦截
        }
    }) {
        public void handleMessage(Message message) {
            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        //子线程更新UI
        //android.view.ViewRootImpl$CalledFromWrongThreadException:
        //Only the original thread that created a view hierarchy can touch its views.
        //        new Thread() {
        //            @Override
        //            public void run() {
        //                try {
        //                    Thread.sleep(1000);
        //                    //handler第三种用法:postDelayed(Runnable)
        //                    handler.post(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            tv.setText("Update");
        //                        }
        //                    });
        //                    //tv.setText("Hello");
        //                } catch (InterruptedException e) {
        //                    e.printStackTrace();
        //                }
        //                super.run();
        //            }
        //        }.start();
        //handler第四种用法:postDelayed(Runnable,long)
        handler.postDelayed(myRunnable, 1000);

        //handler第一种用法:sendMessage(Message)
        //        new Thread() {
        //            @Override
        //            public void run() {
        //                try {
        //                    Thread.sleep(1000);
        //                    //Message message = new Message();
        //                    //Message message = Message.obtain();
        //                    Message message = handlerSendMessage.obtainMessage();
        //                    message.arg1 = 1024;
        //                    message.arg2 = 2048;
        //
        //                    Person person = new Person();
        //                    person.age = 23;
        //                    person.name = "lyd";
        //                    message.obj = person;
        //
        //                    handlerSendMessage.sendMessage(message);
        //                    //message.sendToTarget();
        //
        //                } catch (InterruptedException e) {
        //                    e.printStackTrace();
        //                }
        //                super.run();
        //            }
        //        }.start();
    }


    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        img = (ImageView) findViewById(R.id.img);
        bt = (Button) findViewById(R.id.bt);
    }

    private void initListener() {
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // handler.removeCallbacks(myRunnable);
        handlerCallBack.sendEmptyMessage(1);
    }
}
