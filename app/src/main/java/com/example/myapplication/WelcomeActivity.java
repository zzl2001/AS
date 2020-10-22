package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        User user = (User)bundle.getSerializable("user");
        TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setText(user.getUsername());
        TextView textView1 = (TextView)findViewById(R.id.textView2);
        textView1.setText(user.getPhonenumber());
        TextView textView2 = (TextView)findViewById(R.id.textView3);
        textView2.setText(user.getPwd());
        TextView textView3 = (TextView)findViewById(R.id.textView4);
        textView3.setText(user.getSex());
        TextView textView4 = (TextView)findViewById(R.id.textView5);
        textView4.setText(user.getLove());
        TextView textView5 = (TextView)findViewById(R.id.textView6);
        textView5.setText(user.getStaff());
        ImageView imageView = (ImageView)findViewById(R.id.photo);
        Log.d("msg", user.getUrl());
        imageView.setImageURI(Uri.parse(user.getUrl()));
    }
}
