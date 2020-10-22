package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowActivity extends AppCompatActivity {

    private String fh;
    private RadioButton fuhao;
    private String ta, tb;
    private EditText a, b;
/*    ImageView mImageView ;
    // 顾名思义
    Uri photoUri;

    //系统相册的路径
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;


    //时间戳命名
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
//        mImageView = (ImageView)findViewById(R.id.iv514);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                Intent intent = new Intent(ShowActivity.this, B_Activity.class);
                Intent intent = new Intent("test");
                Bundle bundle = new Bundle();
                fuhao = (RadioButton) findViewById(checkedId);
                fh = fuhao.getText().toString();
                Log.d("msg",fh);
                a = (EditText) findViewById(R.id.editText2);
                b = (EditText) findViewById(R.id.editText3);
                ta = a.getText().toString();
                tb = b.getText().toString();
                bundle.putString("a", ta);
                bundle.putString("b", tb);
                bundle.putString("fuhao", fh);

                intent.putExtras(bundle);
                sendBroadcast(intent);
//                startActivityForResult(intent, 100);
                Log.d("msg","发送了");
            }
        });

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 100);

//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            File file = new File(path);
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            String fileName = getPhotoFileName() + ".jpg";
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            photoUri = Uri.fromFile(new File(path + fileName));
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//            startActivityForResult(intent, 100);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                Double s = bundle.getDouble("sum");
                TextView tv = (TextView) findViewById(R.id.textView9);
                tv.setText("结果=" + s.toString());
            }
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        if (requestCode == 100) {
//            Log.d("msg", "开始回调");
//            Uri uri = null;
//            if (data != null && data.getData() != null) {
//                uri = data.getData();
//            }
//            if (uri == null) {
//                if (photoUri != null) {
//                    uri = photoUri;
////                    mImageView.setImageURI(uri);
//                }
//            }
//        }
//    }
}
