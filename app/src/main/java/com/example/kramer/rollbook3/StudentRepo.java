package com.example.kramer.rollbook3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kramer on 2016/11/20.
 */
public class StudentRepo {
    private DBHelper dbHelper;

    public StudentRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student){
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_icon,student.icon);
        values.put(Student.KEY_num,student.num);
        values.put(Student.KEY_name,student.name);
        long student_Id = db.insert(Student.TABLE,null,values);
        db.close();
        return (int)student_Id;
    }
    public void delete(String num){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Student.TABLE,Student.KEY_num+"=?", new String[]{String.valueOf(num)});
        db.close();
    }
    public void update(Student student){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Student.KEY_icon,student.icon);
        values.put(Student.KEY_num,student.num);
        values.put(Student.KEY_name,student.name);

        db.update(Student.TABLE,values,Student.KEY_num+"=?",
                new String[] { String.valueOf(student.num)});
        db.close();
    }
    public ArrayList<HashMap<String, String>> getStudentList(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery="SELECT "+
                Student.KEY_id+","+
                Student.KEY_name+","+
                Student.KEY_num+","+
                Student.KEY_icon+" FROM "+Student.TABLE+
                " ORDER BY " + Student.KEY_num;
        ArrayList<HashMap<String,String>> studentList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> student=new HashMap<String,String>();
                student.put("id",cursor.getString(cursor.getColumnIndex(Student.KEY_id)));
                student.put("name",cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                student.put("num",cursor.getString(cursor.getColumnIndex(Student.KEY_num)));
                student.put("icon", Base64.encodeToString(
                        cursor.getBlob(cursor.getColumnIndex(Student.KEY_icon)), Base64.DEFAULT));
                studentList.add(student);
            }while(cursor.moveToNext());
        }
        cursor.close();

        db.close();
        return studentList;
    }
    public Student getStudentById(String num){                      //根据学号查找学生信息
        boolean exist = false;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Student.KEY_name + "," +
                Student.KEY_num +"," +
                Student.KEY_icon+
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_num + "=?";
        Student student = new Student();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{num});
        if(cursor.moveToFirst()){
            do{
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.num =cursor.getString(cursor.getColumnIndex(Student.KEY_num));
                student.icon=cursor.getBlob(cursor.getColumnIndex(Student.KEY_icon));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return student;
    }


}
