package com.example.guanguannfc.model.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.example.guanguannfc.model.GuanContract;
import com.example.guanguannfc.model.GuanSQLHelper;
import com.example.guanguannfc.model.Helper.HelperUserInfo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户信息表操作类
 */
public class DaoUserInfo {
    private final GuanSQLHelper mDataBaseHelper;


    public DaoUserInfo(Context context){
        mDataBaseHelper=new GuanSQLHelper(context);

    }
    //

    //插入一个用户：需要给定用户名和密码

    public String insert(String username,String password){

        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="insert into " + GuanContract.UserInfo.TABLE_NAME+ "(user_name,password,created_time,updated_time) values(?,?,?,?)";

        Date date = new Date();
        long currentTime = date.getTime();
        try{
            db.execSQL(sql,new Object[]{username,md5(password),currentTime,currentTime});
        }catch (SQLiteConstraintException e){


        }finally {
            db.close();
        }

        return HttpUtil.post("http://49.232.151.194/DaoUserInfo/insert/", "username=" + username + "&password=" + password);


    }

    //更新引导页位置，
    public boolean updateStudy(String username,int is_studied){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="update " + GuanContract.UserInfo.TABLE_NAME + " set is_studied=? , updated_time=? where user_name=?";

        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{is_studied,currentTime,username});
        db.close();
        return true;
    }

    //删除一个用户：需要给定用户名
    public boolean delete(String username){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql = "delete from " + GuanContract.UserInfo.TABLE_NAME + " where user_name=?";
        db.execSQL(sql,new Object[]{username});
        db.close();
        return true;

    }
    //更新密码：需要给定用户名和密码
    public boolean update(String username,String newPassword){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="update " + GuanContract.UserInfo.TABLE_NAME + " set password=? , updated_time=? where user_name=?";

        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{md5(newPassword),currentTime,username});
        db.close();
        return true;

    }
    //根据用户名查询得到用户的全部信息
    public HelperUserInfo queryUser(String username){
        HelperUserInfo helperUserInfo = new HelperUserInfo();
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="select * from " + GuanContract.UserInfo.TABLE_NAME + " where user_name=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        //Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{"username","password"},"username=? and password=?",new String[]{username,password},null,null,null);
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()) {
                helperUserInfo.setId(cursor.getInt(0));
                helperUserInfo.setUser_name(cursor.getString(1));
                helperUserInfo.setPassword(cursor.getString(2));
                helperUserInfo.setActive_day(cursor.getInt(3));
                helperUserInfo.setLast_act(cursor.getString(4));
                helperUserInfo.setRank(cursor.getInt(5));
                helperUserInfo.setIs_studied(cursor.getInt(6));
                helperUserInfo.setCreated_time(cursor.getLong(7));
                helperUserInfo.setUpdateed_time(cursor.getLong(8));
            }
        }
        return helperUserInfo;

    }

    //登录查询：需要给定用户名和密码

    public String loadQuery(String username,String password){
        String local = null;
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="select * from " + GuanContract.UserInfo.TABLE_NAME + " where user_name=? and password=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,md5(password)});
        //Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{"username","password"},"username=? and password=?",new String[]{username,password},null,null,null);
        if(cursor.getCount()!=0){
            local =  "登录成功";
        }else {
            local =  "登陆失败";
        }
        if ("登录成功".equals(local)) return "登录成功";
        return HttpUtil.get("http://49.232.151.194/DaoUserInfo/loadQuery/", "username=" + username + "&password=" + password);
    }
    //注册查询用户名是否存在：需要给定用户名
    public boolean registrationQuery(String username){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="select * from " + GuanContract.UserInfo.TABLE_NAME + " where user_name=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        if(cursor.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }
    //更改用户表最后一次活动记录时间:需给定用户名、最后一次活动时间（2020年5月12日）
    public boolean updateLastAct(String username,String last_act){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="update user_info set last_act=?,updated_time=? where _id=(select _id from user_info where user_name=?)";
        Date date = new Date();
        long currentTime = date.getTime();
        db.execSQL(sql,new Object[]{last_act,currentTime,username});
        db.close();
        return true;
    }
    //比较给定日期是否和用户最后活动日期相同，相同为true、不同为false：需给定用户民和比较日期（2020年5月12日）
    public boolean queryLastActDate(String username,String last_act_date){
        SQLiteDatabase db=mDataBaseHelper.getWritableDatabase();
        String sql="select last_act from user_info where user_name=? and last_act=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,last_act_date});
        if(cursor.getCount()!=0){
            return true;
        }else {
            return false;
        }
    }
    //活跃天数加一:给定用户名
    public boolean updateActiveDay(String username){
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql=" update user_info set active_day=active_day+1 where user_name=?";
        db.execSQL(sql,new String[]{username});
        return true;
    }

//    获取用户的等级（数组中的第一个元素）、活跃天数：需要给定用户名称
    public String[] personMessage(String username){
        String[] str = new String[2];
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        String sql = "select active_day/2,active_day from user_info where user_name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                str[0] = cursor.getInt(0)+"";
                str[1] = cursor.getInt(1)+"";
                return str;
            }


        }
        return str;
    }

//md5加密
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result="";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length()==1) {
                    temp = "0" + temp;
                }
                result+=temp;
            }
            return result;

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    };
}
