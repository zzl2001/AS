package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends AppCompatActivity {

    private final String msg = "msg";
    private Button button;
    private TextView et_name ;
    private TextView et_tele ;
    private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String[] contacts;
    private TelephonyManager telephonyManager;
    private Intent intentPhone;

    //来去电状态的监听
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Toast.makeText(OtherActivity.this,"挂断",Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Toast.makeText(OtherActivity.this,"接听",Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(OtherActivity.this,"响铃",Toast.LENGTH_LONG).show();
                    break;
            }
//            super.onCallStateChanged(state, phoneNumber);
        }
    };

    private void initTele(){
        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager!=null){
            telephonyManager.listen(mPhoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    //注意一定要先申请权限 当api>23
    private void judge() {
        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    OtherActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            //getPhoneContacts(uri);
            Toast.makeText(getApplicationContext(),"k",Toast.LENGTH_LONG);
        }
    }
    private String[] getPhoneContacts(Uri uri){
        contacts = new String[2];
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contacts[0]=cursor.getString(nameFieldColumnIndex);
            Log.d("msg",contacts[0]);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //Log.d("msg",ContactId);
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);

            if(phone != null){
                phone.moveToFirst();
                contacts[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d("msg",contacts[1]);
            }
            phone.close();
            cursor.close();
        }
        else
        {
            return null;
        }
        return contacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other2);
        //先授权
        judge();
        //如果没有授权就退出
        button  = (Button)findViewById(R.id.btn_contact);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到通讯录
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);
                Toast.makeText(getApplicationContext(),"选择要监听的号码",Toast.LENGTH_LONG).show();
                Log.d(msg, "getInfo");
            }
        });

        Button phone = (Button)findViewById(R.id.phone);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE},1000
            );
        }
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //发送broadcast
                Intent intentbroad = new Intent("aaa");
                sendBroadcast(intentbroad);
                //直接拨号
                intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contacts[1]));
                startActivity(intentPhone);

                //直接监听
                //initTele();
            }
        });
    }

    //事件得单向传递
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if (data == null){
                return;
            }
            Uri uri = data.getData();
            getPhoneContacts(uri);
            if(contacts[0].equals("")||contacts[1].equals(""))
                Toast.makeText(getApplicationContext(),"没有添加成功",Toast.LENGTH_LONG).show();
            et_name = (TextView)findViewById(R.id.et_name);
            et_tele = (TextView)findViewById(R.id.et_tele);
            //此处一定要记得find之后再用 不然报错
            et_name.setText("name:"+contacts[0]);
            et_tele.setText("tele:"+contacts[1]);
        }
    }

    //回调方法，无论哪种结果，最终都会回调该方法，之后在判断用户是否授权，
    // 用户同意则调用readContacts（）方法，失败则会弹窗提示失败
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "授权之后才能使用", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

}
