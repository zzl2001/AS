package com.example.testservice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = (Button) findViewById(R.id.btn_start);
        Button bt2 = (Button) findViewById(R.id.btn_pause);
        Button bt3 = (Button) findViewById(R.id.btn_restart);
        Button bt4 = (Button) findViewById(R.id.btn_stop);
        Button bt5 = (Button) findViewById(R.id.btn_exit);
        View.OnClickListener onclicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_start:
                        op = 1;
                        break;
                    case R.id.btn_pause:
                        op = 2;
                        break;
                    case R.id.btn_restart:
                        op = 3;
                        break;
                    case R.id.btn_stop:
                        op = 0;
                        break;
                    case R.id.btn_exit:
                        op = -1;
                        break;
                    default:
                        break;
                }
                final Intent intent = new Intent(MainActivity.this, MyService.class);
                if (op != -1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("op", op);
                    intent.putExtras(bundle);
                    Log.d("msg", ""+op);
                    startService(intent);
                } else {
                    new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("确认要退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            stopService(intent);
                            finish();
                        }
                    }).setNegativeButton("取消", null).show();
                }
            }
        }; //listener

        bt1.setOnClickListener(onclicklistener);
        bt2.setOnClickListener(onclicklistener);
        bt3.setOnClickListener(onclicklistener);
        bt4.setOnClickListener(onclicklistener);
        bt5.setOnClickListener(onclicklistener);
    }
}