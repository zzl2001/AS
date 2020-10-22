package com.example.test;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneListener extends PhoneStateListener {

    private MediaRecorder mediaRecorder;
    //电话状态改变

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);
        try {
            switch (state) {
                //无电话打入
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mediaRecorder != null) {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }
                    break;
                //响铃得时候
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d("msg", "准备录制"+phoneNumber);

                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //此处只实现了录本地MIC输入的声音,若想录入对立通话者的声音
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //路径
                    File path = Environment.getExternalStorageDirectory();
                    //检查是否符合
                    String file_path = Environment.getExternalStorageDirectory()+"/Media";
                    File file = new File(file_path);
                    if (!file.exists()){
                        file.mkdir();
                    }
                    mediaRecorder.setOutputFile( path+"/"
                            + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())  + ".3gp");
                    //path
                    Log.d("msg",Environment.getExternalStorageDirectory().getAbsolutePath()+"/"
                            + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())  + ".3gp");
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mediaRecorder.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaRecorder.start();
                    Log.d("msg","success");

                    break;
                //电话接通状态  模拟机的没有去点的缓存时间
                case TelephonyManager.CALL_STATE_OFFHOOK: //来电接通 或者 去电，去电接通  但是没法区分
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //此处只实现了录本地MIC输入的声音,若想录入对立通话者的声音
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //路径
                    path = Environment.getExternalStorageDirectory();
                    //检查是否符合
                    file_path = Environment.getExternalStorageDirectory()+"/Media";
                    file = new File(file_path);
                    if (!file.exists()){
                        file.mkdir();
                    }
                    mediaRecorder.setOutputFile( path
                            + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".3gp");
                    //path
                    Log.d("msg",Environment.getExternalStorageDirectory().getAbsolutePath()
                            + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".3gp");
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mediaRecorder.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaRecorder.start();
                    Log.d("msg","success");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
