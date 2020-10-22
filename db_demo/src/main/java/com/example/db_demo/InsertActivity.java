package com.example.db_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {


    private EditText name;
    private ImageView imageView;
    private EditText dsc;
    private String photo_url;

    Button btn_save;
    Button btn_cancel;

    private String DB_Name = "test.db";
    private int DB_Version = 1;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        init();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_save:
                        dbManager = new DBManager(getApplicationContext(), DB_Name,DB_Version);
                        System.out.println(name.getText().toString()+" "+photo_url+" "+dsc.getText().toString());
                        //Teacher teacher =new Teacher(name.getText().toString(),photo_url, dsc.getText().toString());
                        boolean flag = dbManager.insert(name.getText().toString(),photo_url,dsc.getText().toString());
                        if(flag){
                            Toast.makeText(getApplicationContext(), "数据保存成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "数据保存出错", Toast.LENGTH_LONG).show();
                        }
                        setResult(RESULT_OK, null);
                        finish();
                        break;
                    case R.id.btn_cancel:
                        finish();
                        break;
                    case R.id.imageView:
                        //此处用action_pick就错了
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                        startActivityForResult(intent, 1);
                }
            }
        };
        btn_save.setOnClickListener(listener);
        btn_cancel.setOnClickListener(listener);
        imageView.setOnClickListener(listener);
    }

    //界面元素初始化
    public void init(){
        name = (EditText) findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.imageView);
        dsc = (EditText) findViewById(R.id.editText);
        name.setText("abc");
        dsc.setText("说明");
        System.out.println(R.drawable.ic_launcher_background);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                imageView.setImageURI(data.getData());
                photo_url = data.getData().toString();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
