package com.example.kramer.rollbook3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kramer on 2016/11/20.
 */
public class DBHelper extends SQLiteOpenHelper{
    //数据库版本号
    private static final int DATABASE_VERSION = 5;
    //数据库名称
    private static final String DATABASE_NAME = "crud.db";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_STUDENT="CREATE TABLE "+ Student.TABLE+"("
                +Student.KEY_id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +Student.KEY_name+" TEXT, "
                +Student.KEY_num+" TEXT, "
                +Student.KEY_icon + " BLOB)";
        db.execSQL(CREATE_TABLE_STUDENT);

        String CREATE_TABLE_ATTENDANCE="CREATE TABLE "+ Attendance.TABLE+"("
                +Attendance.KEY_id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +Attendance.KEY_num+" TEXT, "
                +Attendance.KEY_week+" INTEGER, "
                +Attendance.KEY_period + " INTEGER, "
                +Attendance.KEY_situation + " TEXT)";
        db.execSQL(CREATE_TABLE_ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ Student.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ Attendance.TABLE);
        //再次创建表
        onCreate(db);

    }
}
