package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    public static final String Tag = "msg";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        Log.d(Tag, "intent:"+intent.getAction());

        //如果是去电
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(action)){
            //拨号 号码
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Toast.makeText( context, "监听电话:" +phoneNumber , Toast.LENGTH_SHORT ).show();
            if (!TextUtils.isEmpty(phoneNumber)){
                Log.i(Tag,"phoneNumber:"+phoneNumber);
            }
        }else {
            //状态
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (phoneState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                Log.i(Tag,"挂断");
            }else if (phoneState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Log.i(Tag,"接听");
            }else if (phoneState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                //号码
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.i(Tag,"响铃"+phoneNumber);
            }
        }
    }
}
