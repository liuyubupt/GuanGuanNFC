package com.example.guanguannfc.model.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSONArray;
import com.example.guanguannfc.model.GuanSQLHelper;
import com.example.guanguannfc.model.Helper.HelperActivity;
import com.example.guanguannfc.model.Helper.HelperApplication;
import com.example.guanguannfc.model.Helper.HelperFriend;
import com.example.guanguannfc.model.util.HttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 好友关系表
 */
public class DaoMoment {
    private final GuanSQLHelper mDataBaseHelper;


    public DaoMoment(Context context){
        mDataBaseHelper=new GuanSQLHelper(context);

    }
    //插入一条数据
    public boolean insert (int from_id,int to_id,String content,int is_processed){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="insert into moment_list(from_id,to_id,content,is_processed,created_time,updated_time) values(?,?,?,?,?,?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{from_id,to_id,content,is_processed,currentTime,currentTime});
        db.close();
        return true;
    }
    //插入一条数据(发送好友申请)：需给定用户名、对方用户名、申请内容
    public boolean insert (String from_name,String to_name,String content){
//        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
//        String sql="insert into moment_list(from_id,to_id,content,created_time,updated_time) values((select _id from user_info where user_name=?),(select _id from user_info where user_name=?),?,?,?)";
//        Date date = new Date();
//        long currentTime = date.getTime();
//        db.execSQL(sql,new Object[]{from_name,to_name,content,currentTime,currentTime});
//        db.close();
        String post = HttpUtil.post("http://49.232.151.194/DaoMoment/insert2/", "from_name=" + from_name + "&to_name=" + to_name + "&content=" + content);

        return "True".equals(post);
    }
    //删除一条数据:需给定用户名、和申请来源用户名
    public boolean delete (String user_name,String friend_name){
//        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
//        String sql="delete from moment_list where from_id=(select _id from user_info where user_name=?) and to_id=(select _id from user_info where user_name=?)";
//        db.execSQL(sql,new Object[]{friend_name,user_name});
//        db.close();
        String post = HttpUtil.post("http://49.232.151.194/DaoMoment/delete/", "user_name=" + user_name + "&friend_name=" + friend_name);

        return "True".equals(post);
    }
    //更新申请处理状态（不管是同意、拒绝，都需要执行该操作）:需给定用户名、和申请来源用户名
    public boolean update (String user_name,String friend_name){
//        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
//        String sql="update moment_list set is_processed=1,updated_time=? where from_id=(select _id from user_info where user_name=?) and to_id=(select _id from user_info where user_name=?)";
//        Date date = new Date();
//        long currentTime = date.getTime();
//        db.execSQL(sql,new Object[]{currentTime,friend_name,user_name});
//        db.close();
        String post = HttpUtil.post("http://49.232.151.194/DaoMoment/update/", "user_name=" + user_name + "&friend_name=" + friend_name);

        return "True".equals(post);
    }

    //返回所有的好友申请列表：需要给定用户名
    public List<HelperApplication> query(String user_name){
//        List<HelperApplication> list = new ArrayList<HelperApplication>();
//        HelperApplication helperApplication;
//        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
//        String sql="select user_name,content,moment_list.created_time from moment_list inner join user_info on user_info._id=moment_list.from_id " +
//                " where to_id=(select _id from user_info where user_name=?) and is_processed=0";
//        Cursor cursor=db.rawQuery(sql,new String[]{user_name});
//        if(cursor.getCount()!=0){
//            while(cursor.moveToNext()){
//                helperApplication = new HelperApplication(cursor.getString(0),cursor.getString(1),cursor.getLong(2));
//                list.add(helperApplication);
//            }
//            return list;
//        }else {
//            return list;
//        }
        String get = HttpUtil.get("http://49.232.151.194/DaoMoment/query/", "user_name=" + user_name);
        if ("网络故障".equals(get) || "空".equals(get)) return new ArrayList<HelperApplication>();
        List<HelperApplication> list = JSONArray.parseArray(get, HelperApplication.class);

        return list != null ? list : new ArrayList<HelperApplication>();
    }




}
