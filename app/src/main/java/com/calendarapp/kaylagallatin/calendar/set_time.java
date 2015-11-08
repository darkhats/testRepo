package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TimePicker;
import android.util.Log;

=======
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

public class set_time extends AppCompatActivity {

    private Button cancelTime;
<<<<<<< HEAD
    private Button saveButton;
=======
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelTime = (Button) this.findViewById(R.id.cancelTimeButton);
        cancelTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
                finish();
            }
        });
        saveButton = (Button) this.findViewById(R.id.saveTimeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(add_event.isStartTime)
                    add_event.startTime = (TimePicker)findViewById(R.id.timePicker);
                else
                    add_event.endTime = (TimePicker)findViewById(R.id.timePicker);
                finish();
=======
                startActivity(new Intent(set_time.this, add_event.class));
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
            }
        });
    }

}
