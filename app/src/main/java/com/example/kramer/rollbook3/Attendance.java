package com.example.kramer.rollbook3;

/**
 * Created by Kramer on 2016/12/1.
 */

public class Attendance {

    //表名
    static final String TABLE= "Attendance";
    //表属性
    static final String KEY_id = "id";
    static final String KEY_num = "num";
    static final String KEY_week = "week";
    static final String KEY_period = "period";
    static final String KEY_situation = "situation";
    static final String KEY_time = "time";
    //属性
    public int id;
    public String num;
    public int week;
    public int period;
    public int time;
    public String situation;

    Attendance(){}
    Attendance(int week,int period,String situation){
        this.week = week;
        this.period = period;
        this.situation = situation;
    }
    public int getWeek(){
        return week;
    }
    public int getPeriod(){
        return period;
    }
    public String getSituation(){
        return situation;
    }

}
