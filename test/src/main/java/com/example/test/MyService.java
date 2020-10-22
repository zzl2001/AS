package com.example.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {

    private MediaRecorder mediaRecorder;

    public MyService() {
    }

    @Override
    public void onCreate() {

        super.onCreate();
        System.out.println("服务开启");
        //电话管理器,它能够获取电话的各种信息
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //监听手机的通话状态的变化
        tm.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // 服务停止时，取消电话状态的监听
        System.out.println("服务销毁");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class PhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);

            /*
             * 具体判断电话的状态
             * */
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: // 空闲状态
                    Log.d("msg", "空闲状态！");
                    if (mediaRecorder != null) {
                        Log.d("msg", "挂断！");
                        mediaRecorder.stop();
                        mediaRecorder.reset();
                        mediaRecorder.release();
                        Log.d("msg","记录成功v");
                        stopSelf();
                        Log.d("msg","记录成功v");
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 接听状态
                    Log.d("msg", "接听！");
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 音频来源
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 设置输出格式 3gp格式
                    createRecorderFile();//创建保存录音的文件夹
//                    File sdFilepath = Environment.getExternalStorageDirectory();
//
//                    File path=new File(sdFilepath,"a.txt");//sd卡下面的a.txt文件  参数 前面 是目录 后面是文件
//                    try {
//                        FileOutputStream fileOutputStream=new FileOutputStream(path);
//                        fileOutputStream.write("hello".getBytes());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory() +
                            "//"  + getCurrentTime() + ".3gp"); //设置录音保存的文件

                    Log.d("msg",getApplicationContext().getFilesDir().getAbsolutePath());
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); //设置音频编码方式
                    try {
                        mediaRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaRecorder.start();

                    break;

                case TelephonyManager.CALL_STATE_RINGING: //响铃状态
                    Log.d("msg", "响铃状态！");
                    // 设置音频来源
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 音频来源
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // 设置输出格式 3gp格式
                    createRecorderFile();//创建保存录音的文件夹
                    File path1 = Environment.getExternalStorageDirectory();
                    mediaRecorder.setOutputFile(path1 + "//Media//" + getCurrentTime() + ".3gp"); //设置录音保存的文件
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); //设置音频编码方式
                    try {
                        mediaRecorder.prepare(); // 准备录
                        Log.d("msg", "准备录音！");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        private void createRecorderFile() {
            String filePath = Environment.getExternalStorageDirectory().toString();
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        private String getCurrentTime() {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String str = format.format(date);
            return str;

        }
    }
}



