package com.example.dialogmachine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ChatMessage> list;//存放临时数据
    private ListView listview;//布局
    private EditText input;//输入
    private Button send;//发送按钮
    private ChatMessageAdapter chatAdapter;//适配器
    private ChatMessage chatMessage = null;//实体类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        setContentView(R.layout.activity_main);
        initView();//初始视图
        initListener();//初始监听器
        initData();//初始化数据
    }


    // 初始视图
    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        input = (EditText) findViewById(R.id.chat_input_message);//输入框
        send = (Button) findViewById(R.id.send);
    }

    // 设置监听事件
    private void initListener() {
        send.setOnClickListener(onClickListener);
    }

    // 初始化数据
    private void initData() {
        list = new ArrayList<ChatMessage>();
        list.add(new ChatMessage("您好,小洛为您服务!", ChatMessage.Type.INCOUNT, new Date()));//添加一列信息
        chatAdapter = new ChatMessageAdapter(list);
        listview.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }


    // 发送消息聊天
    // 使用多线程new Thread 然后run
    /*
    new Message对象， 一个message有两个元素 what obj，然后用handler sendMessage
    message.obj 是object对象 可以存任意实体类
    * */
    private void chat() {
        //判断是否输入内容
        final String send_message = input.getText().toString().trim();
        if (TextUtils.isEmpty(send_message)) {
            Toast.makeText(MainActivity.this, "对不起，您还未发送任何消息",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 记录刷新
        ChatMessage sendChatMessage = new ChatMessage();
        sendChatMessage.setMessage(send_message);
        sendChatMessage.setDate(new Date());
        sendChatMessage.setType(ChatMessage.Type.OUTCOUNT);
        list.add(sendChatMessage);
        chatAdapter.notifyDataSetChanged();
        input.setText("");
        // 发送消息去服务器端，返回数据
        new Thread() {
            public void run() {//发送消息
                ChatMessage chat = HttpRequest.sendMessage(send_message);
                Message message = new Message();
                message.what = 0x1;
                message.obj = chat;
                handler.sendMessage(message);
            }
        }.start();
    }

    /**
     * new Handler对象
     * 实现handleMessage方法
     * 如果what =设置的what
     * 如果obj 不为空 就将为强转为要的实体类，然后添加
     * 可以使用chatAdapter 提醒数据更新
     */
    //处理接受到的信息
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {//处理消息
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0x1) {
                if (msg.obj != null) {
                    chatMessage = (ChatMessage) msg.obj;
                }
                //更新数据
                list.add(chatMessage);
                chatAdapter.notifyDataSetChanged();
            }
        }
    };

    // 点击事件监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.send:
                    chat();
                    break;
            }
        }
    };
}
