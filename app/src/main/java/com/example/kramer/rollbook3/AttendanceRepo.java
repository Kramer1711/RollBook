package com.example.kramer.rollbook3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kramer on 2016/12/1.
 */

public class AttendanceRepo {
    private DBHelper dbHelper;

    public AttendanceRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Attendance a){
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Attendance.KEY_week,a.week);
        values.put(Attendance.KEY_period,a.period);
        values.put(Attendance.KEY_num,a.num);
        values.put(Attendance.KEY_situation,a.situation);
        db.insert(Attendance.TABLE,null,values);
        db.close();
        return 0;
    }

    //按学号查找学生出勤情况
    public ArrayList<HashMap<String,String>> getAttendanceById(String num){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<HashMap<String,String>> AttendanceList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT situation,week,period FROM ATTENDANCE WHERE num =? ORDER BY week";
        Cursor cursor = db.rawQuery(selectQuery,new String[]{num});
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> attendance = new HashMap<String,String>();
                attendance.put("situation",cursor.getString(cursor.getColumnIndex("situation")));
                attendance.put("week",cursor.getString(cursor.getColumnIndex("week")));
                attendance.put("period",cursor.getString(cursor.getColumnIndex("period")));
                AttendanceList.add(attendance);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return AttendanceList;
    }
}
