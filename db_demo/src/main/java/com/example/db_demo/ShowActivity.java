package com.example.db_demo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private ListView listView;
    private TextView msg;
    private Button btn_add;
    private String DB_Name = "test.db";
    private static int DB_Vesion = 1;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        if (ActivityCompat.checkSelfPermission(ShowActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果没有授权，则申请权限
            ActivityCompat.requestPermissions(ShowActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            return;
        }

        init();

        init_listView();
        //注册到listview上
        registerForContextMenu(listView);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, InsertActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    //ListView数据集(存放查询Person表结果)
    List<Teacher> teacherList = new ArrayList<Teacher>();
    //ListView适配器
    PersonAdapter personAdapter;

    //加载选项菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("操作");
        getMenuInflater().inflate(R.menu.manage, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail:
                detail(item);
                return true;
            case R.id.delete:
                delete(item);
                return true;
            case R.id.update:
                update(item);
                return true;
            default:
                return false;
        }
    }

    public void detail(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView txt_name = (TextView) info.targetView.findViewById(R.id.txt_name);
        String wantToSearch = txt_name.getText().toString().split(" ")[0];
        dbManager = new DBManager(ShowActivity.this, DB_Name, DB_Vesion);
        Teacher teacher = dbManager.selectByName(wantToSearch);
        Intent intent = new Intent(ShowActivity.this, TeacherDetailActivity.class);
        intent.putExtra("teacher_image", teacher.getImageId());
        intent.putExtra("teacher_name",teacher.getName());
        intent.putExtra("teacher_desc",teacher.getDesc());
                // 准备跳转
        startActivity(intent);
    }

    public void update(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView txt_name = (TextView) info.targetView.findViewById(R.id.txt_name);
        String wantToSearch = txt_name.getText().toString().split(" ")[0];
        dbManager = new DBManager(ShowActivity.this, DB_Name, DB_Vesion);
        Teacher teacher = dbManager.selectByName(wantToSearch);
        if (teacher!=null){
            Toast.makeText(getApplicationContext(), "查询成功",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(ShowActivity.this, UpdateActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url",teacher.getImageId());
            bundle.putString("name",teacher.getName());
            bundle.putString("dsc",teacher.getDesc());
            intent.putExtras(bundle);
            startActivityForResult(intent, 300);
        }else {
            Toast.makeText(getApplicationContext(), "查询失败",Toast.LENGTH_LONG).show();
        }
    }

    public void delete(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final TextView txt_name = (TextView) info.targetView.findViewById(R.id.txt_name);
        final String wanttodeletename = txt_name.getText().toString().split(" ")[0];
        if (!wanttodeletename.isEmpty()){
            new AlertDialog.Builder(this).setTitle("删除"+txt_name.getText().toString())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbManager = new DBManager(ShowActivity.this, DB_Name, DB_Vesion);
                            boolean flag = dbManager.deleteByName(wanttodeletename);
                            if(flag){
                                Toast.makeText(getApplicationContext(),"记录删除成功",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"记录删除失败",Toast.LENGTH_LONG).show();
                            }
                            init_listView();//刷新一下
                        }
                    }).setNegativeButton("取消",null).show();
        }
    }


    public void init() {
        listView = (ListView) findViewById(R.id.listView1);
        msg = (TextView) findViewById(R.id.msg);
        btn_add = (Button) findViewById(R.id.show);
    }

    public void init_listView() {
        //初始化业务对象
        dbManager = new DBManager(ShowActivity.this, DB_Name, DB_Vesion);
        teacherList = dbManager.getAll();
        personAdapter = new PersonAdapter(ShowActivity.this, R.layout.list_item, teacherList);
        System.out.println("listView");
        for(int i=0;i<teacherList.size();i++){
            System.out.println(teacherList.get(i).getImageId());
        }
        listView.setAdapter(personAdapter);

        msg.setText("查询到" + teacherList.size() + "条记录");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100||requestCode==300)
            if (resultCode == RESULT_OK) {
                init_listView();
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功，把处理函数调用一下
                init_listView();
            } else {
                //授权拒绝，友情提示一下
                Toast.makeText(getApplicationContext(), "没有授权无法操作", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
