package com.example.imagecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private SeekBar seekBar;
    private TextView textView;
    private int x=0;//最小的
    private Bitmap temp;
    private int original_X;

    public void init(){
        seekBar = (SeekBar) findViewById(R.id.sb_2);
        imageView = (ImageView) findViewById(R.id.iv_1);
        textView = (TextView) findViewById(R.id.tv_1);

        imageView.setImageResource(R.drawable.st);
        //设置bitmap图片
        temp = BitmapFactory.decodeResource(getResources(), R.drawable.st);

        //初始imageview的宽度
        ConstraintLayout.LayoutParams para;
        para = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        original_X = para.width;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //屏幕宽度
        int screen = original_X*3;
        //设置硕放滑动最大值
        seekBar.setMax(screen);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newWidth = progress + x;
                int newHeight = (int) (newWidth * temp.getHeight()/temp.getWidth());//按照原图片的比例缩放
                imageView.setLayoutParams(new ConstraintLayout.LayoutParams(newWidth, newHeight));
                textView.setText("放大比例:" + String.format("%.2f",newWidth*1.0/original_X));
                //textView.setText("x:"+newWidth+"y"+newHeight);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
