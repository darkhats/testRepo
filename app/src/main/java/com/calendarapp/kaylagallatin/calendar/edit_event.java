package com.calendarapp.kaylagallatin.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.GregorianCalendar;


public class edit_event extends AppCompatActivity {

    private Button backToCalendar;
    private Button selectedStartDateButton;
    private Button selectedStartTimeButton;
    private Button selectedEndDateButton;
    private Button selectedEndTimeButton;
    protected static TimePicker startTime;
    protected static TimePicker endTime;
    private SQLiteDatabase db;
    protected static boolean isStartTime;
    protected static boolean startTimeClicked = false;
    protected static boolean endTimeClicked = false;
    protected static boolean startDateClicked = false;
    protected static boolean endDateClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedEndDateButton = (Button) this.findViewById(R.id.selectedEditEndDate);
        selectedEndDateButton.setText("Date");
        selectedEndDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endDateClicked = true;
                startActivity(new Intent(edit_event.this, set_date.class));
            }
        });

        selectedStartTimeButton = (Button) this.findViewById(R.id.selectedEditStartTime);
        selectedStartTimeButton.setText("Start");
        selectedStartTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTimeClicked = true;
                startActivity(new Intent(edit_event.this, set_time.class));

                isStartTime = true;

            }
        });

        selectedEndTimeButton = (Button) this.findViewById(R.id.selectedEditEndTime);
        selectedEndTimeButton.setText("End");
        selectedEndTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(edit_event.this, set_time.class));
                endTimeClicked = true;
                isStartTime = false;
            }
        });

        Button save = (Button) this.findViewById(R.id.saveEditEventButton);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                db = mDbHelper.getWritableDatabase();
                EditText editTextCategory = (EditText) findViewById(R.id.edit_event_category_edit);
                String QueryCat = "SELECT * FROM categories WHERE categoryName = ?";
                Cursor curCat = db.rawQuery(QueryCat, new String[]{editTextCategory.getText().toString()});
                if (!endDateClicked || !endTimeClicked || !startTimeClicked) {
                    Toast toastTime = Toast.makeText(edit_event.this, "Looks like you forgot to set a date or time. Please do that. ", Toast.LENGTH_SHORT);
                    toastTime.show();}
                else {
                    if (editTextCategory.getText().toString().length() > 0 && curCat.getCount() == 0) {
                        Toast toastCat = Toast.makeText(edit_event.this, "Invalid category. Please enter a valid category. ", Toast.LENGTH_SHORT);
                        toastCat.show();
                    } else {
                        String Query = "SELECT * FROM events WHERE ((dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?))";
                        Integer tempMonth = set_date.editTextStartDateMonth.getMonth() + 1;
                        CheckBox box = (CheckBox) findViewById(R.id.editChkWindows);
                        GregorianCalendar temp = new GregorianCalendar(set_date.editTextStartDateMonth.getYear(), set_date.editTextStartDateMonth.getMonth(), set_date.editTextStartDateMonth.getDayOfMonth());
                        Integer tempDayOfWeek = (box.isChecked() ? temp.get(temp.DAY_OF_WEEK) : 99);
                        Integer tempDay = set_date.editTextStartDateMonth.getDayOfMonth();
                        Integer tempYear = set_date.editTextStartDateMonth.getYear();
                        Cursor cur = db.rawQuery(Query, new String[]{tempDayOfWeek.toString(), "99", tempMonth.toString(), tempDay.toString(), tempYear.toString()});
                        boolean valid = true;
                        if (cur.getCount() > 0) {
                            cur.moveToFirst();
                            while (cur.isAfterLast() == false) {
                                int dbStartTime = Integer.parseInt(cur.getString(8)) * 60 + Integer.parseInt(cur.getString(9));
                                int dbEndTime = Integer.parseInt(cur.getString(10)) * 60 + Integer.parseInt(cur.getString(11));
                                int inputStartTime = startTime.getCurrentHour() * 60 + startTime.getCurrentMinute();
                                int inputEndTime = endTime.getCurrentHour() * 60 + endTime.getCurrentMinute();

                                if (dbStartTime <= inputStartTime && dbEndTime >= inputStartTime)
                                    valid = false;
                                else if (dbStartTime <= inputEndTime && dbEndTime >= inputEndTime)
                                    valid = false;
                                else if (inputStartTime <= dbStartTime && dbEndTime <= inputEndTime)
                                    valid = false;
                                cur.moveToNext();
                            }
                        }
                        if (valid) {
                            saveEvent();
                            finish();
                        } else {
                            Toast toast = Toast.makeText(edit_event.this, "You're already busy during that time!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            }
        });


        EditText editTextTitle = (EditText)findViewById(R.id.edit_event_title_edit);
        editTextTitle.setText(choose_edit_event.title);

        EditText editTextLocation = (EditText)findViewById(R.id.edit_event_location_edit);
        editTextLocation.setText(choose_edit_event.location);

        EditText editTextDescription = (EditText)findViewById(R.id.edit_event_description_edit);
        editTextDescription.setText(choose_edit_event.description);

        EditText editTextCategory = (EditText)findViewById(R.id.edit_event_category_edit);
        editTextCategory.setText(choose_edit_event.category);

        backToCalendar = (Button) this.findViewById(R.id.backToCalendar);
        backToCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        ContentValues values = new ContentValues();
        //  values.put(DatabaseHelper.TITLE, id);


    }

    protected void saveEvent()
    {
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();
        GregorianCalendar temp = new GregorianCalendar(set_date.editTextStartDateMonth.getYear(),set_date.editTextStartDateMonth.getMonth(),set_date.editTextStartDateMonth.getDayOfMonth());
        EditText editTextTitle = (EditText)findViewById(R.id.edit_event_title_edit);
        EditText editTextDescription = (EditText)findViewById(R.id.edit_event_description_edit);
        EditText editTextLocation = (EditText)findViewById(R.id.edit_event_location_edit);
        EditText editTextCategory = (EditText)findViewById(R.id.edit_event_category_edit);
        CheckBox box = (CheckBox)findViewById(R.id.editChkWindows);
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
        values.put(DatabaseHelper.CATEGORY,editTextCategory.getText().toString());

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

        startDateClicked = false;
        startTimeClicked = false;
        endTimeClicked = false;
        endDateClicked = false;

    }

}