package com.example.kramer.rollbook3;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Kramer on 2016/11/28.
 */
public class EditDetail extends AppCompatActivity {

    private String num;
    private EditText sname,snum;
    private StudentRepo repo;
    private Student student;
    private ImageView icon;
    private Bitmap bitmap1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        Intent intent = getIntent();
        Bundle db = intent.getExtras();
        num = db.getString("num");

        repo = new StudentRepo(this);
        student = repo.getStudentById(num);

        sname = (EditText) findViewById(R.id.editTextSName);
        snum = (EditText)findViewById(R.id.editTextSNum);
        icon = (ImageView)findViewById(R.id.imageViewicon);

        sname.setText(student.name);
        snum.setText(student.num);
        Bitmap bitmap = BitmapFactory.decodeByteArray(student.icon, 0, student.icon.length);
        icon.setImageBitmap(bitmap);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开本地图库
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        Button button = (Button)findViewById(R.id.addStudentSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = sname.getText().toString();
                String newnum = snum.getText().toString();
                student.name = newname;
                student.icon = getImg();
                //student.num = newnum;//????
                repo.update(student);
                Intent intent1 = new Intent(EditDetail.this,Detail.class);
                Bundle bd2 = new Bundle();
                bd2.putString("num",num);
                intent1.putExtras(bd2);
                startActivity(intent1);
            }
        });
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
                    //图片
                    bitmap1 = MediaStore.Images.Media.getBitmap(resolver, uri);
                    icon.setImageBitmap(bitmap1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] getImg(){                 //将图片转化为BLOB
        //Bitmap bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.pika);            //将文件里的图片转化为Bitmap
        Bitmap bitmap1=drawableToBitmap(icon.getDrawable());
        int size=bitmap1.getWidth()*bitmap1.getHeight()*4;
        ByteArrayOutputStream baos=new ByteArrayOutputStream(size);
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
