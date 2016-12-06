package com.example.kramer.rollbook3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Kramer on 2016/11/30.
 */

public class ChoicePeriod extends AppCompatActivity{

    int weeks = 0,periods = 0,time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_period);

        Spinner spinner1 = (Spinner)findViewById(R.id.spinner_week);
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner_period);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weeks = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periods = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button rollStart = (Button)findViewById(R.id.start_roll);
        rollStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoicePeriod.this,RollFirst.class);
                Bundle bd = new Bundle();
                bd.putInt("week",weeks);
                bd.putInt("period",periods);
                bd.putInt("time",time);
                intent.putExtras(bd);
                startActivity(intent);
            }
        });
    }
}
