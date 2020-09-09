package com.example.guanguannfc.model;

import android.provider.BaseColumns;

public class GuanContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "guan.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String UNIQUE_TYPE = " UNIQUE";
    private static final String COMMA_SEP = " ,";

    private GuanContract(){}

    public static class UserInfo implements BaseColumns{
        public static final String TABLE_NAME = "User_Info";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ACTIVE_DAY = "active_day";
        public static final String COLUMN_LAST_ACT = "last_act";
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_STUDY = "is_studied";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + UserInfo.TABLE_NAME + " (" +
                UserInfo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                UserInfo.COLUMN_USER_NAME + TEXT_TYPE + UNIQUE_TYPE + COMMA_SEP +
                UserInfo.COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
                UserInfo.COLUMN_ACTIVE_DAY + INT_TYPE + " DEFAULT 0" + COMMA_SEP +
                UserInfo.COLUMN_LAST_ACT + TEXT_TYPE + " DEFAULT '1970年1月1日'" + COMMA_SEP +
                UserInfo.COLUMN_RANK + INT_TYPE + " DEFAULT 0" + COMMA_SEP +
                UserInfo.COLUMN_STUDY + INT_TYPE + " DEFAULT 0" + COMMA_SEP +
                UserInfo.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                UserInfo.COLUMN_UPDATED_TIME + INT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + UserInfo.TABLE_NAME;
    }

    public static class ActivityType implements BaseColumns{
        public static final String TABLE_NAME = "Activity_Type";
        public static final String COLUMN_ACT_TYPE = "act_type";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + ActivityType.TABLE_NAME + " (" +
                ActivityType._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                ActivityType.COLUMN_ACT_TYPE + TEXT_TYPE + COMMA_SEP +
                ActivityType.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                ActivityType.COLUMN_UPDATED_TIME + INT_TYPE + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ActivityType.TABLE_NAME;
    }

    public static class Activity implements BaseColumns{
        public static final String TABLE_NAME = "Activity";
        public static final String COLUMN_USER_ID = "user_ID";
        public static final String COLUMN_NFC = "nfc";
        public static final String COLUMN_TYPE_ID = "type_ID";
        public static final String COLUMN_ACT_NAME = "act_name";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + Activity.TABLE_NAME + " (" +
                Activity._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                Activity.COLUMN_USER_ID + INT_TYPE + COMMA_SEP +
                Activity.COLUMN_NFC + TEXT_TYPE + COMMA_SEP +
                Activity.COLUMN_TYPE_ID + INT_TYPE + COMMA_SEP +
                Activity.COLUMN_ACT_NAME + TEXT_TYPE + COMMA_SEP +
                Activity.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                Activity.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + Activity.COLUMN_USER_ID + ") REFERENCES " + UserInfo.TABLE_NAME + "(" + UserInfo._ID + ")" + COMMA_SEP +
                " FOREIGN KEY (" + Activity.COLUMN_TYPE_ID + ") REFERENCES " + ActivityType.TABLE_NAME + "(" + ActivityType._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Activity.TABLE_NAME;
    }

    public static class ActSta implements BaseColumns{
        public static final String TABLE_NAME = "Act_Sta";
        public static final String COLUMN_ACT_ID = "act_ID";
        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_END_TIME = "end_time";
        public static final String COLUMN_MOMENT_TEXT = "moment_text";
        public static final String COLUMN_IS_SHARED = "is_shared";
        public static final String COLUMN_SHARED_TIME = "shared_time";
        public static final String COLUMN_IS_RANKED = "is_ranked";
        public static final String COLUMN_RANKED_TIME = "ranked_time";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + ActSta.TABLE_NAME + " (" +
                ActSta._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                ActSta.COLUMN_ACT_ID + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_START_TIME + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_END_TIME + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_MOMENT_TEXT + TEXT_TYPE + " default ''" + COMMA_SEP +
                ActSta.COLUMN_IS_SHARED + INT_TYPE + " default 0" + COMMA_SEP +
                ActSta.COLUMN_SHARED_TIME + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_IS_RANKED + INT_TYPE + " default 0" + COMMA_SEP +
                ActSta.COLUMN_RANKED_TIME + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                ActSta.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + ActSta.COLUMN_ACT_ID + ") REFERENCES " + Activity.TABLE_NAME + "(" + Activity._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ActSta.TABLE_NAME;
    }

    public static class Box implements BaseColumns{
        public static final String TABLE_NAME = "Box";
        public static final String COLUMN_USER_ID = "user_ID";
        public static final String COLUMN_NFC = "nfc";
        public static final String COLUMN_BOX_NAME = "box_name";
        public static final String COLUMN_BOX_POS = "box_pos";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + Box.TABLE_NAME + " (" +
                Box._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                Box.COLUMN_USER_ID + INT_TYPE + COMMA_SEP +
                Box.COLUMN_NFC + TEXT_TYPE + COMMA_SEP +
                Box.COLUMN_BOX_NAME + TEXT_TYPE + COMMA_SEP +
                Box.COLUMN_BOX_POS + TEXT_TYPE + COMMA_SEP +
                Box.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                Box.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + Box.COLUMN_USER_ID + ") REFERENCES " + UserInfo.TABLE_NAME + "(" + UserInfo._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Box.TABLE_NAME;
    }

    public static class BoxContent implements BaseColumns{
        public static final String TABLE_NAME = "Box_Content";
        public static final String COLUMN_BOX_ID = "box_ID";
        public static final String COLUMN_THING_NAME = "thing_name";
        public static final String COLUMN_THING_NUM = "thing_num";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + BoxContent.TABLE_NAME + " (" +
                BoxContent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                BoxContent.COLUMN_BOX_ID + INT_TYPE + COMMA_SEP +
                BoxContent.COLUMN_THING_NAME + TEXT_TYPE + COMMA_SEP +
                BoxContent.COLUMN_THING_NUM + INT_TYPE + COMMA_SEP +
                BoxContent.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                BoxContent.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + BoxContent.COLUMN_BOX_ID + ") REFERENCES " + Box.TABLE_NAME + "(" + Box._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + BoxContent.TABLE_NAME;
    }

    public static class FRIEND implements BaseColumns{
        public static final String TABLE_NAME = "Friend_List";
        public static final String COLUMN_USER_ID = "user_ID";
        public static final String COLUMN_FRIEND_ID = "friend_ID";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + FRIEND.TABLE_NAME + " (" +
                FRIEND._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                FRIEND.COLUMN_USER_ID + INT_TYPE + COMMA_SEP +
                FRIEND.COLUMN_FRIEND_ID + INT_TYPE + COMMA_SEP +
                FRIEND.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                FRIEND.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + FRIEND.COLUMN_USER_ID + ") REFERENCES " + UserInfo.TABLE_NAME + "(" + UserInfo._ID + ")" + COMMA_SEP +
                " FOREIGN KEY (" + FRIEND.COLUMN_FRIEND_ID + ") REFERENCES " + UserInfo.TABLE_NAME + "(" + UserInfo._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FRIEND.TABLE_NAME;

    }

    public  static class APPLICATION implements BaseColumns{
        public static final String TABLE_NAME = "Moment_List";
        public static final String COLUMN_FROM_ID = "from_ID";
        public static final String COLUMN_TO_ID = "to_ID";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_IS_PROCESSED = "is_processed";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + APPLICATION.TABLE_NAME + " (" +
                APPLICATION._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                APPLICATION.COLUMN_FROM_ID + INT_TYPE + COMMA_SEP +
                APPLICATION.COLUMN_TO_ID + INT_TYPE + COMMA_SEP +
                APPLICATION.COLUMN_CONTENT + TEXT_TYPE + COMMA_SEP +
                APPLICATION.COLUMN_IS_PROCESSED + INT_TYPE + " DEFAULT 0" + COMMA_SEP +
                APPLICATION.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                APPLICATION.COLUMN_UPDATED_TIME + INT_TYPE  + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + APPLICATION.TABLE_NAME;


    }

    public static class PUSHNOTE implements BaseColumns{
        public static final String TABLE_NAME = "Push_Note";
        public static final String COLUMN_AUTHOR_ID = "author_ID";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_CONTENTS = "contents";
        public static final String COLUMN_CREATED_TIME = "created_time";
        public static final String COLUMN_UPDATED_TIME = "updated_time";
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + PUSHNOTE.TABLE_NAME + " (" +
                PUSHNOTE._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                PUSHNOTE.COLUMN_AUTHOR_ID + INT_TYPE + COMMA_SEP +
                PUSHNOTE.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                PUSHNOTE.COLUMN_SUMMARY + TEXT_TYPE + COMMA_SEP +
                PUSHNOTE.COLUMN_CONTENTS + TEXT_TYPE + COMMA_SEP +
                PUSHNOTE.COLUMN_CREATED_TIME + INT_TYPE + COMMA_SEP +
                PUSHNOTE.COLUMN_UPDATED_TIME + INT_TYPE + COMMA_SEP +
                " FOREIGN KEY (" + PUSHNOTE.COLUMN_AUTHOR_ID + ") REFERENCES " + UserInfo.TABLE_NAME + "(" + UserInfo._ID + ")"
                + " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + PUSHNOTE.TABLE_NAME;
    }

}
