package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.util.Log;
import android.widget.Toast;


public class set_time extends AppCompatActivity {

    private Button cancelTime;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelTime = (Button) this.findViewById(R.id.cancelTimeButton);
        cancelTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        saveButton = (Button) this.findViewById(R.id.saveTimeButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(edit_event.startTimeClicked && edit_event.isStartTime)
                    edit_event.startTime = (TimePicker)findViewById(R.id.timePicker);
                else if(edit_event.endTimeClicked)
                    edit_event.endTime = (TimePicker)findViewById(R.id.timePicker);
                else if(add_event.isStartTime)
                    add_event.startTime = (TimePicker)findViewById(R.id.timePicker);
                else
                    add_event.endTime = (TimePicker)findViewById(R.id.timePicker);
                if(edit_event.startTimeClicked && !edit_event.isStartTime || !add_event.isStartTime && add_event.startTimeClicked)
                {
                      boolean startTimeAndEndTimeValid = true;
                       if(edit_event.startTimeClicked)
                       {
                           int inputStartTime = edit_event.startTime.getCurrentHour() * 60 + edit_event.startTime.getCurrentMinute();
                           int inputEndTime = edit_event.endTime.getCurrentHour() * 60 + edit_event.endTime.getCurrentMinute();
                           startTimeAndEndTimeValid = inputEndTime >= inputStartTime;

                       }
                        else
                       {
                           int inputStartTime = add_event.startTime.getCurrentHour() * 60 + add_event.startTime.getCurrentMinute();
                           int inputEndTime = add_event.endTime.getCurrentHour() * 60 + add_event.endTime.getCurrentMinute();
                           startTimeAndEndTimeValid = inputEndTime >= inputStartTime;

                       }
                    if(startTimeAndEndTimeValid)
                        finish();
                    else {
                        Toast toast = Toast.makeText(set_time.this, "You're end time is before your start time! Please choose another ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                    finish();
             //   startActivity(new Intent(set_time.this, add_event.class));
            }
        });
    }

}
