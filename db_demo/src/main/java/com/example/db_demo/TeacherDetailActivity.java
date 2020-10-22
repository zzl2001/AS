package com.example.db_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TeacherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        // 从Intent获取数据
        String name = getIntent().getStringExtra("teacher_name");
        String desc = getIntent().getStringExtra("teacher_desc");
        String url = getIntent().getStringExtra("teacher_image");

        // 获取特定的视图
        ImageView imageView = (ImageView) findViewById(R.id.teacher_large_imageView);
        TextView textView = (TextView) findViewById(R.id.teacher_desc_textView);

        // 根据数据设置视图展现
        imageView.setImageURI(Uri.parse(url));
        textView.setText(name + "  "+ desc);
    }
}
