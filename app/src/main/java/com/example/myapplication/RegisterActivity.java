package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {


    ImageView iv;
    private String msg;
    private String photo_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ctrl+alt+l快速对齐
        iv = (ImageView) findViewById(R.id.photo);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0x1);
            }
        });
        Button btn = (Button) findViewById(R.id.btn_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                Bundle bundle = new Bundle();
                EditText username = (EditText) findViewById(R.id.username);
                EditText phone = (EditText) findViewById(R.id.phone);
                EditText pwd = (EditText) findViewById(R.id.pwd);
                RadioGroup sexGroup = (RadioGroup) findViewById(R.id.sexGroup);
                RadioButton sex = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());
                CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
                msg ="";
                if (cb1.isChecked()) {
                    msg += cb1.getText().toString() + " ";
                }
                CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
                if (cb2.isChecked()) {
                    msg += cb2.getText().toString() + " ";
                }
                CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
                if (cb3.isChecked()) {
                    msg += cb3.getText().toString() + " ";
                }
                Spinner m = (Spinner) findViewById(R.id.major);
                

                User user = new User(username.getText().toString(),
                        phone.getText().toString(),
                        pwd.getText().toString(),
                        sex.getText().toString(),
                        msg,
                        m.getSelectedItem().toString(),
                        photo_url);
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                startActivity(intent);
               /* String msg = "";
                msg += "\n用户名=";

                msg += username.getText().toString();
                msg += "\n手机号=";

                msg += phone.getText().toString();
                msg += "\n密码=";

                msg += pwd.getText().toString();
                msg += "\n性别=";
                RadioGroup sexGroup = (RadioGroup) findViewById(R.id.sexGroup);
                RadioButton sex = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());

                msg += "\n爱好=";

                msg += "\n专业=";

                msg += m.getSelectedItem().toString();
                Log.d("msg", "获得的数据如下：" + msg);*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                iv.setImageURI(data.getData());
                photo_url = data.getData().toString();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
