package com.example.kramer.rollbook3;

/**
 * Created by Kramer on 2016/11/20.
 */
public class Student {
    //表名
    static final String TABLE= "Student";
    //表属性
    static final String KEY_id = "id";
    static final String KEY_name = "name";
    static final String KEY_num = "num";
    static final String KEY_icon = "icon";
    //属性
    public int id;
    public String name;
    public String num;
    public byte[] icon;
    Student(){}
    Student(String name,String num,byte[] icon){
        this.name = name;
        this.num = num;
        this.icon = icon;
    }
}
