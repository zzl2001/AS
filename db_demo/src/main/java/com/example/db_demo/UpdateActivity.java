package com.example.db_demo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editdsc;
    Button button_update;
    Button cancel_update;
    ImageView iv;

    private String photo_url;
    private String new_name;
    private String new_dsc;

    private String DBName = "test.db";
    private int DB_Vesion = 1;
    private DBManager dbManager;

    public void init(){
        editTextName = (EditText)findViewById(R.id.updatename);
        editdsc = (EditText)findViewById(R.id.updatedsc);
        button_update = (Button)findViewById(R.id.update_save);
        cancel_update = (Button)findViewById(R.id.cancel_update);
        iv = (ImageView)findViewById(R.id.update_image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //初始化
        init();
        //获取ShowActivity的值
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        photo_url = bundle.getString("url");
        new_name = bundle.getString("name");
        new_dsc = bundle.getString("dsc");

        editTextName.setText(new_name);
        editTextName.setEnabled(false);
        editdsc.setText(new_dsc);
        iv.setImageURI(Uri.parse(photo_url));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
            }
        });


        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_name = editTextName.getText().toString();
                new_dsc = editdsc.getText().toString();

                dbManager = new DBManager(getApplicationContext(), DBName,DB_Vesion);
                boolean flag = dbManager.updateByName(photo_url, new_name, new_dsc);
                if(flag){
                    Toast.makeText(getApplicationContext(), "记录修改成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_LONG).show();
                }
                setResult(RESULT_OK, null);
                finish();
            }
        });

        cancel_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode== RESULT_OK){
            iv.setImageURI(data.getData());
            photo_url = data.getData().toString();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
