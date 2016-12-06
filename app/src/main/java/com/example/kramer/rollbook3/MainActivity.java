package com.example.kramer.rollbook3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private List<StudentItem> mData = null;
    private Context mContext;
    private StudentAdapter mAdapter = null;
    private ListView list_student;
    private LinearLayout ly_content;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        list_student = (ListView) findViewById(R.id.list_student);

        mData = new LinkedList<StudentItem>();

        StudentRepo repo = new StudentRepo(this);
        //读取数据库中所有的学生保存到一个hash表的list中
        ArrayList<HashMap<String,String>> listHashStudent = repo.getStudentList();
        if(listHashStudent.size() != 0){
            while (i < listHashStudent.size()){
                HashMap<String,String> student = listHashStudent.get(i++);
                String name = student.get("name");
                String num = student.get("num");
                byte[] icon = Base64.decode(student.get("icon"), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length);

                //添加数据到mdata
                mData.add(new StudentItem(name,num,bitmap));
            }
        }
        //将mDate中的数据添加到适配器中
        mAdapter = new StudentAdapter((LinkedList<StudentItem>) mData, mContext);

        //给listView设置适配器
        list_student.setAdapter(mAdapter);
        //添加点击Item的事件
        list_student.setOnItemClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至添加学生界面
                Intent intent = new Intent(MainActivity.this,AddStudent.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击item,获取学号并进入学生详情界面
        TextView content=(TextView) view.findViewById(R.id.txt_sNum);
        Intent intent = new Intent(MainActivity.this,Detail.class);
        Bundle bd = new Bundle();
        bd.putString("num",(String)content.getText());
        intent.putExtras(bd);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //开始点名
            Intent intent = new Intent(MainActivity.this,ChoicePeriod.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
