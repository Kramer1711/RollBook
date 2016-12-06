package com.example.kramer.rollbook3;

import android.graphics.Bitmap;

/**
 * Created by Kramer on 2016/11/20.
 */

//用于解决显示list中Item时适配的问题
public class StudentItem {
    private String sName;
    private String sNum;
    private Bitmap sIcon;

    public StudentItem() {
    }

    public StudentItem(String sName, String sNum, Bitmap sIcon) {
        this.sName = sName;
        this.sNum = sNum;
        this.sIcon = sIcon;
    }

    public String getsName() {
        return sName;
    }

    public String getsNum() {
        return sNum;
    }

    public Bitmap getsIcon() {
        return sIcon;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public void setsIcon(Bitmap sIcon) {
        this.sIcon = sIcon;
    }
}
