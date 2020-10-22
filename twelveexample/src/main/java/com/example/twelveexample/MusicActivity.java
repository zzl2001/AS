package com.example.twelveexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class MusicActivity extends AppCompatActivity {

    SeekBar seekBar;
    MediaPlayer mp = new MediaPlayer();
    TextView current_time;
    TextView total_time;
    Button btn_play;
    int duration = 0;
    int current = 0;
    boolean isOver = false;//播放完毕

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==0){
                if(!isOver){
                    current = mp.getCurrentPosition();
                    seekBar.setProgress(current);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    current_time.setText(simpleDateFormat.format(current));
                }
            }
        }
    };

    public void init_view() {
        seekBar = (SeekBar) findViewById(R.id.musicSeekBar);
        current_time = (TextView) findViewById(R.id.current_time);
        total_time = (TextView) findViewById(R.id.total_time);
        btn_play = (Button) findViewById(R.id.btn_play);
    }

    public void requirePermission() {
        if (ActivityCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MusicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
    }

    public void loadMusic(){
        String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String music_path = sdpath + "/Music/街道办GDC _ 欧阳耀莹 - 春娇与志明.mp3";
        try {
            mp.reset();
            mp.setDataSource(music_path);
            mp.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void playMusic(){
        duration = mp.getDuration();
        seekBar.setMax(duration);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        total_time.setText(sdf.format(duration));
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOver = false;
                if (mp.isPlaying()){
                    mp.pause();
                    btn_play.setText("播放");
                }else{
                    btn_play.setText("暂停");
                    mp.start();
                    //启动进程
                    new Thread(){
                        @Override
                        public void run() {
                            while (!isOver){
                                try {
                                    Thread.sleep(50);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }.start();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        requirePermission();
        init_view();
        loadMusic();
        playMusic();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current = seekBar.getProgress();
                mp.seekTo(current);
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                current_time.setText(sdf.format(current));
            }
        });

        //复原
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"结束",Toast.LENGTH_LONG).show();
                isOver = true;
                btn_play.setText("播放");
                current = 0;
                current_time.setText(""+current);
                seekBar.setProgress(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOver=true;
        if (mp!=null){
            mp.stop();
            mp.release();
        }
        Toast.makeText(getApplicationContext(), "退出", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //授权成功，把处理函数调用一下
                loadMusic();
                playMusic();
            }
        } else {
            //拒绝
        }
    }
}
