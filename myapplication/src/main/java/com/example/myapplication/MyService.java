package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service {
    MediaPlayer mp = new MediaPlayer();
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null) {
            Bundle bundle = intent.getExtras();
            int op = bundle.getInt("op");
            String song_path = bundle.getString("songpath");
            Log.d("msg",song_path + " op:"+op);
            switch (op){
                case 0:
                    try {
                        mp.reset();
                        mp.setDataSource(song_path);  //设置音乐源
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                    break;
                case 1:
                    if (mp.isPlaying()){
                        mp.pause();
                        Log.d("msg","stop");
                    }else {
                        mp.start();
                        Log.d("msg","start");
                    }
                    break;
                case 2:
                    if (mp!=null) {
                        try {
                            mp.reset();
                            mp.setDataSource(song_path);  //设置音乐源
                            mp.prepare();     //准备
                            mp.start(); //播放
                        } catch (Exception e) {
                        }
                    }
                    else {
                        Log.d("msg","mp为null");
                    }
                    break;
                case 3:
                    if (mp!=null){
                        try {
                            mp.reset();
                            mp.setDataSource(song_path);  //设置音乐源
                            mp.prepare();     //准备
                            mp.start(); //播放
                        } catch (Exception e) {
                        }
                    }else{
                    Log.d("msg","mp为null");
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        Log.d("msg","close");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
