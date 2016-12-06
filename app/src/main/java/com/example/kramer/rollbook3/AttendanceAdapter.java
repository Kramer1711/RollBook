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
 * Created by Kramer on 2016/12/1.
 */

public class AttendanceAdapter extends BaseAdapter {
    private LinkedList<Attendance> mData;
    private Context mContext;

    public AttendanceAdapter(LinkedList<Attendance> mData, Context mContext){
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_attendance,parent,false);
        TextView textWeek = (TextView) convertView.findViewById(R.id.item_text2);
        TextView textPeriod = (TextView) convertView.findViewById(R.id.item_text4);
        TextView textSituation = (TextView) convertView.findViewById(R.id.item_text6);

        textSituation.setText(mData.get(position).getSituation()+"    ");

        String s1= String.valueOf(mData.get(position).getWeek());
        String s2= String.valueOf(mData.get(position).getPeriod());
        textWeek.setText(s1);
        textPeriod.setText(s2);
        return convertView;
    }
}
