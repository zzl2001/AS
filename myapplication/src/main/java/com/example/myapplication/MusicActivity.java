package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {

    List<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView li;//控件
    MediaPlayer mp = new MediaPlayer();
    String song_path = "";//绝对路径
    File path; //文件夹路径
    long isplayingindex; //标志播放的序列
    private SeekBar seekBar;//进度条
    private TextView description;
    private boolean isSeek;
    private int op;//给service操作
    ImageButton btnpause;
    String nextsongpath=null;
    private Intent intent;
    private Bundle bundle;

    public void makePause() {
        final ImageButton btnpause = (ImageButton) findViewById(R.id.btn_pause);
        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song_path.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "先选歌听听",
                            Toast.LENGTH_SHORT).show();
                }
                if (mp.isPlaying()) {
                    mp.pause();
                    btnpause.setImageResource(R.drawable.play);
                } else if (!song_path.isEmpty()) {
                    mp.start();
                    btnpause.setImageResource(R.drawable.pause);
                }
            }
        });
    }

    public void nextMusic() {
        final ImageButton btnnext = (ImageButton) findViewById(R.id.next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song_path.isEmpty()) {
//                    song_path = path + "/" + list.get(0);
//                    li.setItemChecked(0, true);
//                    Toast.makeText(getApplicationContext(), "当前没选择故从第一个开始", Toast.LENGTH_SHORT).show();
//                    try {
//                        mp.reset();    //重置
//                        mp.setDataSource(song_path);  //设置音乐源
//                        mp.prepare();     //准备
//                        mp.start(); //播放
//                    } catch (Exception e) {
//                    }
                    Toast.makeText(getApplicationContext(), "先选歌听听",
                            Toast.LENGTH_SHORT).show();
                } else {
                    isplayingindex = (isplayingindex + 1) % list.size();
                    li.setItemChecked((int) isplayingindex, true);
                    song_path = path + "/" + list.get((int) isplayingindex);
                    Log.d("msg", song_path);
                    try {
                        mp.reset();
                        mp.setDataSource(song_path);  //设置音乐源
                        mp.prepare();     //准备
                        mp.start(); //播放
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    createSeekBar();
                    Toast.makeText(getApplicationContext(),
                            "选中了:" + list.get((int) isplayingindex) + ",id=" + isplayingindex,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void lastMusic() {
        ImageButton butlast = (ImageButton) findViewById(R.id.last);
        butlast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song_path.isEmpty()) {
//                    song_path = path + "/" + list.get(0);
//                    li.setItemChecked(0, true);
//                    Toast.makeText(getApplicationContext(), "当前没选择故从第一个开始", Toast.LENGTH_SHORT).show();
//                    try {
//                        mp.reset();    //重置
//                        mp.setDataSource(song_path);  //设置音乐源
//                        mp.prepare();     //准备
//                        mp.start(); //播放
//                    } catch (Exception e) {
//                    }
                    Toast.makeText(getApplicationContext(), "先选歌听听",
                            Toast.LENGTH_SHORT).show();
                } else {
                    isplayingindex = (isplayingindex - 1 + list.size()) % list.size();
                    li.setItemChecked((int) isplayingindex, true);
                    song_path = path + "/" + list.get((int) isplayingindex);
                    Log.d("msg", song_path);
                    try {
                        mp.reset();
                        mp.setDataSource(song_path);  //设置音乐源
                        mp.prepare();     //准备
                        mp.start(); //播放
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    createSeekBar();
                    Toast.makeText(getApplicationContext(),
                            "选中了:" + list.get((int) isplayingindex) + ",id=" + isplayingindex,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        description = (TextView) findViewById(R.id.textView);
        Log.d("msg",""+mp.getDuration());
        seekBar.setMax(mp.getDuration()/1000);
        isSeek = false;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                description.setText( String.format("%02d",mp.getCurrentPosition()/1000/60)+":"+
                        String.format("%02d",mp.getCurrentPosition()/1000%60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress()*1000);
                //Log.d("msg",""+seekBar.getProgress());
                isSeek = false;
            }

        });
        Log.d("msg",""+seekBar.getMax());
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isSeek) {
                        seekBar.setProgress(mp.getCurrentPosition()/1000);
                        //Log.d("msg",""+mp.getCurrentPosition());
                    }
                }
            }
        }.start();
    }

    class MyFilter implements FilenameFilter {
        private String type;

        public MyFilter(String type) {
            //构造函数
            this.type = type;
        }

        @Override
        //实现接口accept()方法
        public boolean accept(File dir, String name) {
            //dir当前目录, name文件名
            return name.endsWith(type);
            //返回true的文件则合格 }
        }
    }

    /*加载列表*/
    public void data() {
        File sdpath = Environment.getExternalStorageDirectory();
        path = new File(sdpath + "//Music//");

        File[] songFiles = path.listFiles(new MyFilter(".mp3"));

        for (File file : songFiles) {
            String name = (String) file.getAbsolutePath();
            name = name.split("/")[file.getAbsoluteFile().toString().split("/").length - 1];
            System.out.println(name);
            list.add(name);
        }
    }

    public void createAdapter() {
        adapter = new ArrayAdapter<String>(
                MusicActivity.this, android.R.layout.simple_list_item_single_choice, list
        );
        li = (ListView) findViewById(R.id.listView);
        li.setAdapter(adapter);
        li.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void clickEvent() {
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                song_path = path + "/" + ((TextView) view).getText().toString();
                try {
                    mp.reset();    //重
                    mp.setDataSource(song_path);  //设置音乐源
                    mp.prepare();     //准备
                    mp.start(); //播放
                } catch (Exception e) {
                }
                isplayingindex = id;
                Toast.makeText(getApplicationContext(),
                        "选中了:" + ((TextView) view).getText().toString() + ",id=" + id,
                        Toast.LENGTH_SHORT).show();
                createSeekBar();
            }
        });
    }

    public void playMusic() {
        clickEvent();
        createSeekBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        if (ActivityCompat.checkSelfPermission(MusicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则申请权限
            ActivityCompat.requestPermissions(MusicActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        data();
        createAdapter();
        //playMusic();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_pause:
                        song_path = path + "/" + list.get((int) isplayingindex);
                        op=1;
                        break;
                    case R.id.next:
                        op = 2;//next
                        isplayingindex = (isplayingindex + 1) % list.size();
                        li.setItemChecked((int) isplayingindex, true);
                        song_path = path + "/" + list.get((int) isplayingindex);
                        createSeekBar();
                        break;
                    case R.id.last:
                        isplayingindex = (isplayingindex - 1 + list.size()) % list.size();
                        li.setItemChecked((int) isplayingindex, true);
                        song_path = path + "/" + list.get((int) isplayingindex);
                        createSeekBar();
                        op = 3;//last
                        break;
                    default:
                        break;
                }
                intent = new Intent(MusicActivity.this, MyService.class);
                bundle = new Bundle();
                bundle.putInt("op", op);
                bundle.putString("songpath",song_path);
                intent.putExtras(bundle);
                startService(intent);
            }
        };
        btnpause = (ImageButton) findViewById(R.id.btn_pause);
        ImageButton next = (ImageButton) findViewById(R.id.next);
        ImageButton last = (ImageButton) findViewById(R.id.last);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                song_path = path + "/" + ((TextView) view).getText().toString();
                isplayingindex = id;
                Toast.makeText(getApplicationContext(),
                        "选中了:" + ((TextView) view).getText().toString() + ",id=" + id,
                        Toast.LENGTH_SHORT).show();
                createSeekBar();
                intent = new Intent(MusicActivity.this,MyService.class);
                bundle = new Bundle();
                bundle.putString("songpath",song_path);
                bundle.putInt("op",0);
                intent.putExtras(bundle);
                startService(intent);
            }
        });
        btnpause.setOnClickListener(listener);
        next.setOnClickListener(listener);
        last.setOnClickListener(listener);

//        makePause();
//        nextMusic();
//        lastMusic();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
        Toast.makeText(getApplicationContext(), "退出啦", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功，把处理函数调用一下，如 playMusic();
                data();
                createAdapter();
            } else {
                //授权拒绝，友情提示一下
                Toast.makeText(getApplicationContext(), "没有授权无法操作", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
