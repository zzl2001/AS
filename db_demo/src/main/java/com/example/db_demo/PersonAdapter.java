package com.example.db_demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Teacher> {

    private int resourceId;//存放ListView的行布局

    public PersonAdapter(@NonNull Context context, int resource, @NonNull List<Teacher> objects) {
        super(context, resource, objects);
        resourceId = resource;//添加语句
    }

    //一定要重写getView()
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Teacher teacher = getItem(position);//获取当前行的Person实例
        //获取listview行布局及其元素
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView p_Id = (ImageView) view.findViewById(R.id.tcimage);
        TextView p_Name = (TextView) view.findViewById(R.id.txt_name);

        //将teacher数据填充到listview行中(注意数据类型要匹配)
        p_Id.setImageURI(Uri.parse(teacher.getImageId()));
        p_Name.setText(teacher.getName()+" "+teacher.getDesc());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //  初始化一个准备跳转到TeacherDetailActivity的Intent
//                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);
//                intent.putExtra("teacher_image", teacher.getImageId());
//                intent.putExtra("teacher_name",teacher.getName());
//                intent.putExtra("teacher_desc",teacher.getDesc());
//                // 准备跳转
//                getContext().startActivity(intent);
//            }
//        });

        return view;
    }
}
