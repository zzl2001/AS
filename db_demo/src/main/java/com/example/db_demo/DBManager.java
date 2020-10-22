package com.example.db_demo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//业务类
public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    //构造函数：创建DBhelper实例
    public DBManager(Context context,String dbname,int version){
        dbHelper = new DBHelper(context,dbname,null,version);
        //连接OR创建
        db = dbHelper.getWritableDatabase();
    }

    public boolean insert(String name,String url,String dcs){
        boolean flag= false;
        String sql = "INSERT INTO teacher VALUES(?,?,?)";
        db.beginTransaction();//开始事务
        try{
            System.out.println(name+" "+url+" "+dcs);
            db.execSQL(sql, new Object[]{url,name,dcs});
            db.setTransactionSuccessful();
            flag=true;
        }catch (Exception e){
            Log.d("msg","error");
            System.out.println(e);
        }finally {
            db.endTransaction();
            db.close();
        }
        return flag;
    }

    public boolean updateByName(String url, String name,String dcs){
        boolean flag = false;
        String sql = "Update teacher set imageId=?,dsc=? where name=?";
        System.out.println(name+" "+url+" "+dcs);
        db.beginTransaction();
        try{
            db.execSQL(sql, new Object[]{url,dcs,name});
            db.setTransactionSuccessful();
            flag=true;
        }catch (Exception e){
            Log.d("msg","更新失败");
        }finally {
            db.endTransaction();
            db.close();
        }
        return flag;
    }


    public boolean deleteByName(String name){
        boolean flag=false;
        String sql = "Delete from teacher where name=?";
        db.beginTransaction();//开始事务
        try{
            db.execSQL(sql,new Object[]{name});
            db.setTransactionSuccessful();
            flag=true;
        }catch (Exception e){
            Log.d("msg","删除数句失败");
        }finally {
            db.endTransaction();
            db.close();
        }
        return flag;
    }

    public Teacher selectByName(String name){

        String sql = "Select imageId,name,dsc from teacher where name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});

        if(cursor.getCount()==0){
            db.close();
            return null;
        }else{
            cursor.moveToNext();
            String url = cursor.getString(cursor.getColumnIndex("imageId"));
            String this_name = cursor.getString(cursor.getColumnIndex("name"));
            String dsc = cursor.getString(cursor.getColumnIndex("dsc"));
            Teacher teacher = new Teacher(this_name, url, dsc);

            cursor.close();
            db.close();
            return teacher;
        }
    }


    //得到所有的数据
    public List<Teacher> getAll(){
        List<Teacher> list = new ArrayList<Teacher>();
        //结果集
        Cursor cursor = db.rawQuery("SELECT imageId,name,dsc FROM teacher",null);
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("imageId"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String dsc = cursor.getString(cursor.getColumnIndex("dsc"));
            Teacher person = new Teacher(name,id,dsc);
            list.add(person);
        }
        cursor.close();
        db.close();
        return list;
    }
}
