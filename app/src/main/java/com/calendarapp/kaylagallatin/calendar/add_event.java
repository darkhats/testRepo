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
<<<<<<< HEAD
import android.database.*;
=======
import android.database.sqlite.SQLiteDatabase;
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.database.sqlite.*;
import android.app.Activity;
import android.view.*;
import android.content.*;
<<<<<<< HEAD
import android.util.Log;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
=======
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

public class add_event extends AppCompatActivity{

    private Button backToCalendar;
    private Button selectedStartDateButton;
    private Button selectedStartTimeButton;
    private Button selectedEndDateButton;
    private Button selectedEndTimeButton;
<<<<<<< HEAD
    private Button selectedSaveButton;
    public static TimePicker startTime;
    public static TimePicker endTime;
    public static boolean isStartTime;
    public static SQLiteDatabase db;
=======

>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

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
<<<<<<< HEAD
                isStartTime = true;
=======
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
            }
        });

        selectedEndTimeButton = (Button) this.findViewById(R.id.selectedEndTime);
        selectedEndTimeButton.setText("End");
        selectedEndTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(add_event.this, set_time.class));
<<<<<<< HEAD
                isStartTime = false;
            }
        });

        selectedSaveButton = (Button) this.findViewById(R.id.saveAddEventButton);
        selectedSaveButton.setText("Save");
        selectedSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("timeHere", "" + endTime.getCurrentHour());
                saveEvent();
               finish();
            }
        });



=======
            }
        });

>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
        backToCalendar = (Button) this.findViewById(R.id.backToCalendar);
        backToCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

<<<<<<< HEAD



    }

    protected void saveEvent()
    {
        EditText editTextTitle = (EditText)findViewById(R.id.add_event_title_edit);
        EditText editTextDescription = (EditText)findViewById(R.id.add_event_description_edit);
        EditText editTextLocation = (EditText)findViewById(R.id.add_event_location_edit);
        CheckBox box = (CheckBox)findViewById(R.id.chkWindows);
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        db = mDbHelper.getWritableDatabase();
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

        long newRowId;
        newRowId = db.insert(
                "events",
                null,
                values);

        String Query = "SELECT * FROM events";
        Cursor cur = db.rawQuery(Query,null);
        if(cur.moveToFirst())
            for(int k = 1; k < 15; k++)
                Log.d("Item: ",cur.getString(k));

    }
=======
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        //  values.put(DatabaseHelper.TITLE, id);


    }

>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
}
