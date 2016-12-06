package com.example.kramer.rollbook3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Kramer on 2016/11/20.
 */
public class StudentAdapter extends BaseAdapter{

    private LinkedList<StudentItem> mData;
    private Context mContext;

    public StudentAdapter(LinkedList<StudentItem> mData,Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_student,parent,false);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        TextView txt_sName = (TextView) convertView.findViewById(R.id.txt_sName);
        TextView txt_sNum = (TextView) convertView.findViewById(R.id.txt_sNum);
        img_icon.setImageBitmap(mData.get(position).getsIcon());
        txt_sName.setText(mData.get(position).getsName());
        txt_sNum.setText(mData.get(position).getsNum());
        return convertView;
    }

}
