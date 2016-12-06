package com.example.kramer.rollbook3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kramer on 2016/12/2.
 */

public class RollNext extends Activity implements View.OnClickListener{
    private StudentRepo repo = new StudentRepo(this);
    private int index,weeks,periods;
    private Attendance attendance;
    private TextView name,num;
    private Student student;
    private String firstNum;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_attendance_situation);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        index = bd.getInt("index");
        weeks = bd.getInt("week");
        periods = bd.getInt("period");
        ArrayList<HashMap<String,String>> studentList = repo.getStudentList();
        if(index < studentList.size()){
            firstNum = studentList.get(index).get("num");
            student = repo.getStudentById(firstNum);
            Bitmap bitmap = BitmapFactory.decodeByteArray(student.icon, 0, student.icon.length);
            ImageView icon = (ImageView)findViewById(R.id.icon_roll);
            icon.setImageBitmap(bitmap);

            name = (TextView)findViewById(R.id.text1);
            num = (TextView)findViewById(R.id.text2);

            name.setText(student.name);
            num.setText(student.num);
        }else {
            Toast.makeText(this,"点名结束",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(RollNext.this,MainActivity.class);
            startActivity(intent1);
        }

        attendance = new Attendance();
        attendance.week = weeks;
        attendance.period = periods;
        attendance.num = firstNum;

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1: attendance.situation = "出勤"; break;
            case R.id.button2: attendance.situation = "迟到"; break;
            case R.id.button3: attendance.situation = "请假"; break;
            case R.id.button4: attendance.situation = "旷课"; break;
        }
        AttendanceRepo repoAttendance = new AttendanceRepo(this);
        repoAttendance.insert(attendance);
        index++;
        Intent intent = new Intent(RollNext.this,RollNext.class);
        Bundle bd = new Bundle();
        bd.putInt("index",index);
        bd.putInt("week",weeks);
        bd.putInt("period",periods);
        intent.putExtras(bd);
        startActivity(intent);
    }
}
