package com.example.kramer.rollbook3;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Kramer on 2016/11/20.
 */
public class AddStudent extends Activity implements View.OnClickListener{
    String name,num;
    Student student;
    EditText numText,nameText;
    ImageView icon;
    Button save,delete;
    Bitmap bitmap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        nameText = (EditText)findViewById(R.id.editTextSName);
        numText = (EditText)findViewById(R.id.editTextSNum);
        if(num != null){
            StudentRepo repo = new StudentRepo(this);
            Student student = repo.getStudentById(num);
            nameText.setText(student.name);
            numText.setText(student.num);
        }

        icon = (ImageView)findViewById(R.id.imageViewicon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIcon(v);
            }
        });
        save = (Button)findViewById(R.id.addStudentSave);
        save.setOnClickListener(this);
    }


    @Override
    public void onClick(View view){
        if(view == findViewById(R.id.addStudentSave)){
            name = nameText.getText().toString();
            num = numText.getText().toString();
            if(name == null || num == null){
                Toast.makeText(this,"信息不完整",Toast.LENGTH_SHORT);
                return;
            }
            student = new Student(name,num,getImg());

            StudentRepo repo = new StudentRepo(this);
            repo.insert(student);
        }
        Intent intent = new Intent(AddStudent.this,MainActivity.class);
        AddStudent.this.startActivity(intent);
    }
    public void getIcon(View view) {                               //打开系统相册
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver resolver = getContentResolver();
        if (requestCode == 1) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
                    icon.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] getImg(){                 //将图片转化为二进制数据保存
        //将ImageView里的图片转化为Bitmap
        Bitmap bitmap1 = drawableToBitmap(icon.getDrawable());
        int size = bitmap1.getWidth()*bitmap1.getHeight()*4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public Bitmap drawableToBitmap(Drawable drawable) {              // drawable 转换成bitmap
        int width = drawable.getIntrinsicWidth();                   // 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity()
                != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);                         // 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);                                      // 把drawable内容画到画布中
        return bitmap;
    }
}
