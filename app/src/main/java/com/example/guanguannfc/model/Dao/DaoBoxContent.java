package com.example.guanguannfc.model.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guanguannfc.model.GuanContract;
import com.example.guanguannfc.model.GuanSQLHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 盒子内容表操作类
 */
public class DaoBoxContent {
    private final GuanSQLHelper mDataBaseHelper;


    public DaoBoxContent(Context context) {
        mDataBaseHelper = new GuanSQLHelper(context);

    }

    //插入一条盒子物品数据：需要给定用户名，盒子名称、物品名称、物品数量
    public boolean insert(String user_name,String box_name, String thing_name, Integer thing_num) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "insert into Box_Content(box_ID,thing_name,thing_num,created_time,updated_time) values((select _id from Box where box_name=? and user_ID=(select _id from User_Info where user_name=?)),?,?,?,?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql, new Object[]{box_name,user_name,thing_name, thing_num, currentTime, currentTime});
        db.close();
        return true;
    }
    //插入一条盒子物品数据：需要给定盒子id、物品名称、物品数量
    public boolean insert(int box_ID, String thing_name, Integer thing_num) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "insert into Box_Content(box_ID,thing_name,thing_num,created_time,updated_time) values(?,?,?,?,?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql, new Object[]{box_ID,thing_name, thing_num, currentTime, currentTime});
        db.close();
        return true;
    }


    //删除某个盒子的所有物品：需要给定盒子ID
    public boolean delete(long box_ID) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "delete from " + GuanContract.BoxContent.TABLE_NAME + " where box_ID=?";
        db.execSQL(sql, new Object[]{box_ID});
        db.close();
        return true;
    }

    //删除某个盒子中某个物品：需要给定用户名，盒子名称、物品名称
    public boolean delete(String user_name,String box_name, String thing_name) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql =" delete from Box_Content where box_ID=(select _id from Box where box_name=? and user_ID=(select _id from User_Info where user_name=?)) and thing_name=?";
        db.execSQL(sql, new Object[]{box_name, user_name,thing_name});
        db.close();
        return true;
    }

    //更新某个盒子中某个物品的数量：需要给定盒子的ID、物品的名称、更新后物品的数量
    public boolean update(String user_name,String box_name, String thing_name, Integer thing_num) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "update Box_Content set thing_num=? , updated_time=? where box_ID=(select _id from Box where box_name=? and user_ID=(select _id from User_Info where user_name=?)) and thing_name=?";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql, new Object[]{thing_num, currentTime, box_name,user_name, thing_name});
        db.close();
        return true;

    }

    //指定盒子内物品名称查重，存在返回true，反之false：需给定用户名、盒子名称、查重物品名称
    public boolean loadQuery(String user_name,String box_name, String thing_name) {
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "select * from Box_Content where box_ID=(select _id from Box where box_name=? and userID=(select _id from User_Info where user_name=?)) and thing_name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{box_name,user_name,thing_name});
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }
}






