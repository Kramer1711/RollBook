package com.example.kramer.rollbook3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kramer on 2016/11/26.
 */
public class Detail extends AppCompatActivity {
    private String num;
    private TextView nameText,numText;
    ImageView icon;
    private StudentRepo repoStudent;
    private AttendanceRepo repoAttendance;
    private ListView listAttendance;
    private int i = 0;
    private AttendanceAdapter adapter;
    private List<Attendance> mData = null;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_detail);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        num = bd.getString("num");

        setDetail();//设置学生信息
        setAttendance();

        TextView delete = (TextView)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setTitle("警告：")
                        .setMessage("您确定要删除该学生的所有信息吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "成功删除该学生的所有信息", Toast.LENGTH_SHORT).show();
                                repoStudent.delete(num);
                                Intent intent1 = new Intent(Detail.this,MainActivity.class);
                                Detail.this.startActivity(intent1);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
                            }
                        }).create();             //创建AlertDialog对象
                alert.show();                    //显示对话框

            }
        });
        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Detail.this,EditDetail.class);
                Bundle bd2 = new Bundle();
                bd2.putString("num",num);
                intent2.putExtras(bd2);

                Detail.this.startActivity(intent2);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Detail.this,MainActivity.class);
        startActivity(intent);
        return;
    }

    //设置学生信息
    protected void setDetail(){
        repoStudent = new StudentRepo(this);
        Student student = repoStudent.getStudentById(num);

        nameText = (TextView)findViewById(R.id.detailSname);
        numText = (TextView)findViewById(R.id.detailSnum);
        icon = (ImageView)findViewById(R.id.imageView_icon);

        nameText.setText(student.name);
        numText.setText(student.num);
        Bitmap imagebitmap = BitmapFactory.decodeByteArray(student.icon, 0, student.icon.length);
        icon.setImageBitmap(imagebitmap);
    }
    //设置考勤情况
    protected void setAttendance(){
        listAttendance = (ListView)findViewById(R.id.listViewAttendance);
        mContext = Detail.this;
        repoAttendance = new AttendanceRepo(this);
        mData = new LinkedList<Attendance>();
        ArrayList<HashMap<String,String>> listHashAttendance = repoAttendance.getAttendanceById(num);
        if(listHashAttendance.size() != 0){
            while (i < listHashAttendance.size()){
                HashMap<String,String> attendance = listHashAttendance.get(i++);
                String week = attendance.get("week");
                String period = attendance.get("period");
                String situation = attendance.get("situation");
                mData.add(new Attendance(Integer.valueOf(week),Integer.valueOf(period),situation));
            }
        }
        adapter = new AttendanceAdapter((LinkedList<Attendance>) mData, mContext);
        listAttendance.setAdapter(adapter);
    }

}
