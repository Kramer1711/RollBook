package com.example.kramer.rollbook3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Kramer on 2016/12/2.
 */

public class RollFirst extends Activity implements View.OnClickListener{
    private StudentRepo repo = new StudentRepo(this);
    private int weeks,periods,index = 0;
    private TextView name,num;
    private Attendance attendance;
    private String firstNum;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_attendance_situation);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        weeks = bd.getInt("week");
        periods = bd.getInt("period");

        Toast.makeText(this,"第"+weeks+"周，第"+periods+"节课，点名开始！",Toast.LENGTH_SHORT).show();

        ArrayList<HashMap<String,String>> studentList = repo.getStudentList();
        //得到列表中第一名学生的学号
        firstNum = studentList.get(0).get("num");
        student = repo.getStudentById(firstNum);
        Bitmap bitmap = BitmapFactory.decodeByteArray(student.icon, 0, student.icon.length);
        ImageView icon = (ImageView)findViewById(R.id.icon_roll);
        icon.setImageBitmap(bitmap);

        name = (TextView)findViewById(R.id.text1);
        num = (TextView)findViewById(R.id.text2);

        name.setText(student.name);
        num.setText(student.num);

        attendance = new Attendance();
        attendance.week = weeks;
        attendance.period = periods;
        attendance.num = firstNum;

        Button button1 = (Button)findViewById(R.id.button1);//出勤
        Button button2 = (Button)findViewById(R.id.button2);//迟到
        Button button3 = (Button)findViewById(R.id.button3);//请假
        Button button4 = (Button)findViewById(R.id.button4);//旷课

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //保存出勤情况
            case R.id.button1: attendance.situation = "出勤"; break;
            case R.id.button2: attendance.situation = "迟到"; break;
            case R.id.button3: attendance.situation = "请假"; break;
            case R.id.button4: attendance.situation = "旷课"; break;
        }
        AttendanceRepo repoAttendance = new AttendanceRepo(this);
        repoAttendance.insert(attendance);
        index++;
        //转到下一个学生
        Intent intent = new Intent(RollFirst.this,RollNext.class);
        Bundle bd = new Bundle();
        bd.putInt("index",index);
        bd.putInt("week",weeks);
        bd.putInt("period",periods);
        intent.putExtras(bd);
        startActivity(intent);
    }
}
