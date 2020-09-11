package com.example.guanguannfc.model.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guanguannfc.model.GuanContract;
import com.example.guanguannfc.model.GuanSQLHelper;
import com.example.guanguannfc.model.Helper.HelperFriend;
import com.example.guanguannfc.model.Helper.HelperFriendAct;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 好友关系表
 */
public class DaoFriend {
    private final GuanSQLHelper mDataBaseHelper;

    private DaoMoment mDaoMoment;
    public DaoFriend(Context context){
        mDataBaseHelper=new GuanSQLHelper(context);
        mDaoMoment = new DaoMoment(context);

    }
    //插入一条数据
    public boolean insert (int user_id,int friend_id){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="insert into friend_list(user_id,friend_id,created_time,updated_time) values(?,?,?,?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{user_id,friend_id,currentTime,currentTime});
        db.close();
        return true;
    }
    //插入一条数据(添加一个好友)：需要给定用户名和好友名字
    public boolean insert (String user_name,String friend_name){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="insert into friend_list(user_id,friend_id,created_time,updated_time) values((select _id from user_info where user_name=?),(select _id from user_info where user_name=?),?,?)";
        String sql1="insert into friend_list(user_id,friend_id,created_time,updated_time) values((select _id from user_info where user_name=?),(select _id from user_info where user_name=?),?,?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{user_name,friend_name,currentTime,currentTime});
        db.execSQL(sql1,new Object[]{friend_name,user_name,currentTime,currentTime});
        db.close();
        mDaoMoment.update(user_name,friend_name);
        return true;
    }
    //删除一条数据(删除一个好友)：需要给定用户名和好友名字
    public boolean delete (String user_name,String friend_name){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="delete from friend_list where user_id=(select _id from user_info where user_name=?) and friend_id=(select _id from user_info where user_name=?)";
        String sql1="delete from friend_list where user_id=(select _id from user_info where user_name=?) and friend_id=(select _id from user_info where user_name=?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{user_name,friend_name});
        db.execSQL(sql1,new Object[]{friend_name,user_name});
        db.close();
        return true;
    }

    //返回所有好友的用户名和等级：需要给定用户名
    public List<HelperFriend> query(String user_name){

//        HelperFriend helperFriend;
//        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
//        String sql="select user_name,active_day from User_Info where User_Info._id in (select friend_id from friend_list where user_id=(select _id from user_info where user_name=?))";
//        Cursor cursor=db.rawQuery(sql,new String[]{user_name});
//        if(cursor.getCount()!=0){
//            while(cursor.moveToNext()){
//                helperFriend = new HelperFriend(cursor.getString(0),cursor.getInt(1));
//                list.add(helperFriend);
//            }
//            return list;
//        }else {
//            return list;
//        }
        String get = HttpUtil.get("http://49.232.151.194/DaoFriend/query", "user_name=" + user_name);
        if ("网络故障".equals(get)) return new ArrayList<HelperFriend>();
        List<HelperFriend> list = JSONArray.parseArray(get, HelperFriend.class);
        return list != null ? list : new ArrayList<HelperFriend>();
    }

    //拿到所有好朋友分享的动态：需给定用户名
    public List<HelperFriendAct> queryFriendAct(String user_name){
        List<HelperFriendAct> list = new ArrayList<HelperFriendAct>();
        HelperFriendAct helperFriendAct;
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="select user_name,active_day,act_type,start_time,end_time,(end_time-start_time),moment_text,shared_time from " +
                "act_sta inner join activity on act_sta.act_id=activity._id " +
                " inner join user_info on user_info._id=activity.user_id " +
                " inner join activity_type on activity.type_id=activity_type._id " +
                " where user_info._id in (select friend_id from friend_list where user_id=(select _id from user_info where user_name=?))" +
                " and act_sta.is_shared=1";
        Cursor cursor=db.rawQuery(sql,new String[]{user_name});
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                helperFriendAct = new HelperFriendAct(cursor.getString(0),cursor.getInt(1),cursor.getString(2),
                        cursor.getLong(3),cursor.getLong(4),cursor.getLong(5),cursor.getString(6),cursor.getLong(7));
                list.add(helperFriendAct);
            }
            return list;
        }else {
            return list;
        }
    }





}
