package com.calendarapp.kaylagallatin.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Context;

import android.database.*;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.database.sqlite.*;
import android.app.Activity;
import android.view.*;
import android.content.*;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.GregorianCalendar;


public class add_event extends AppCompatActivity{

    private Button backToCalendar;
    private Button selectedStartDateButton;
    private Button selectedStartTimeButton;
    private Button selectedEndDateButton;
    private Button selectedEndTimeButton;

    private Button selectedSaveButton;
    public static TimePicker startTime;
    public static TimePicker endTime;
    public static boolean isStartTime;
    public static  SQLiteDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedStartDateButton = (Button) this.findViewById(R.id.selectedStartDate);
        selectedStartDateButton.setText("Start");
        selectedStartDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(add_event.this, set_date.class));
            }
        });

        selectedEndDateButton = (Button) this.findViewById(R.id.selectedEndDate);
        selectedEndDateButton.setText("End");
        selectedEndDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(add_event.this, set_date.class));
            }
        });

        selectedStartTimeButton = (Button) this.findViewById(R.id.selectedStartTime);
        selectedStartTimeButton.setText("Start");
        selectedStartTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(add_event.this, set_time.class));

                isStartTime = true;

            }
        });

        selectedEndTimeButton = (Button) this.findViewById(R.id.selectedEndTime);
        selectedEndTimeButton.setText("End");
        selectedEndTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(add_event.this, set_time.class));

                isStartTime = false;
            }
        });

        selectedSaveButton = (Button) this.findViewById(R.id.saveAddEventButton);
        selectedSaveButton.setText("Save");
        selectedSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                db = mDbHelper.getWritableDatabase();
                Log.d("timeHere", "" + endTime.getCurrentHour());
                String Query = "SELECT * FROM events WHERE ((dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?))";
                Integer tempMonth = set_date.editTextStartDateMonth.getMonth()+1;
                CheckBox box = (CheckBox)findViewById(R.id.chkWindows);
                GregorianCalendar temp = new GregorianCalendar(set_date.editTextStartDateMonth.getYear(),set_date.editTextStartDateMonth.getMonth(),set_date.editTextStartDateMonth.getDayOfMonth());
                Integer tempDayOfWeek = (box.isChecked()?temp.get(temp.DAY_OF_WEEK):99);
                Integer tempDay = set_date.editTextStartDateMonth.getDayOfMonth();
                Integer tempYear = set_date.editTextStartDateMonth.getYear();
                Cursor cur = db.rawQuery(Query, new String[]{tempDayOfWeek.toString(), "99", tempMonth.toString(), tempDay.toString(), tempYear.toString()});
                boolean valid = true;
                if(cur.getCount() > 0)
                {
                    cur.moveToFirst();
                    while(cur.isAfterLast() == false)
                    {
                        int dbStartTime = Integer.parseInt(cur.getString(8)) * 60 + Integer.parseInt(cur.getString(9));
                        int dbEndTime = Integer.parseInt(cur.getString(10)) * 60 + Integer.parseInt(cur.getString(11));
                        int inputStartTime = startTime.getCurrentHour() * 60 + startTime.getCurrentMinute();
                        int inputEndTime = endTime.getCurrentHour() * 60 + endTime.getCurrentMinute();

                        if(dbStartTime <= inputStartTime && dbEndTime >=inputStartTime)
                            valid=false;
                        else if (dbStartTime<=inputEndTime && dbEndTime>=inputEndTime)
                            valid=false;
                        else if(inputStartTime<=dbStartTime && dbEndTime<=inputEndTime)
                            valid=false;
                        cur.moveToNext();
                    }
                }
                if(valid) {
                    saveEvent();
                    finish();
                }
                else
                {
                    Toast toast = Toast.makeText(add_event.this, "You're already busy during that time! Please choose another " , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });






        backToCalendar = (Button) this.findViewById(R.id.backToCalendar);
        backToCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });





    }


    protected void saveEvent()
    {
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
       GregorianCalendar temp = new GregorianCalendar(set_date.editTextStartDateMonth.getYear(),set_date.editTextStartDateMonth.getMonth(),set_date.editTextStartDateMonth.getDayOfMonth());
        EditText editTextTitle = (EditText)findViewById(R.id.add_event_title_edit);
        EditText editTextDescription = (EditText)findViewById(R.id.add_event_description_edit);
        EditText editTextLocation = (EditText)findViewById(R.id.add_event_location_edit);
        CheckBox box = (CheckBox)findViewById(R.id.chkWindows);
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TITLE,editTextTitle.getText().toString());
        values.put(DatabaseHelper.STARTDATEMONTH,set_date.editTextStartDateMonth.getMonth()+1);
        values.put(DatabaseHelper.STARTDATEDAY,set_date.editTextStartDateMonth.getDayOfMonth());
        values.put(DatabaseHelper.STARTDATEYEAR,set_date.editTextStartDateMonth.getYear());
        values.put(DatabaseHelper.ENDDATEMONTH,set_date.editTextStartDateMonth.getMonth()+1);
        values.put(DatabaseHelper.ENDDATEDAY,set_date.editTextStartDateMonth.getDayOfMonth());
        values.put(DatabaseHelper.ENDDATEYEAR,set_date.editTextStartDateMonth.getYear());
        values.put(DatabaseHelper.STARTTIMEHOUR,startTime.getCurrentHour());
        values.put(DatabaseHelper.STARTTIMEMINUTE,startTime.getCurrentMinute());
        values.put(DatabaseHelper.ENDTIMEHOUR,endTime.getCurrentHour());
        values.put(DatabaseHelper.ENDTIMEMINUTE,endTime.getCurrentMinute());
        values.put(DatabaseHelper.LOCATION,editTextLocation.getText().toString());
        values.put(DatabaseHelper.DESCRIPTION,editTextDescription.getText().toString());
        values.put(DatabaseHelper.PERIODIC,box.isChecked());
        values.put(DatabaseHelper.DAYOFWEEK,(box.isChecked()?temp.get(temp.DAY_OF_WEEK):99));

        long newRowId;
        newRowId = db.insert(
                "events",
                null,
                values);

        String Query = "SELECT * FROM events";
        Cursor cur = db.rawQuery(Query,null);
        if(cur.moveToFirst())
            for(int k = 1; k < 16; k++)
                Log.d("Item: ",cur.getString(k));

    }



}
