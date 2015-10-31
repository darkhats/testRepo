package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pradeep on 10/31/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Calendar.db";
    public static final String TITLE = "title";
    public static final String STARTDATEMONTH = "startDateMonth";
    public static final String STARTDATEDAY = "startDateDay";
    public static final String STARTDATEYEAR = "startDateYear";
    public static final String ENDDATEMONTH = "endDateMonth";
    public static final String ENDDATEDAY = "endDateDay";
    public static final String ENDDATEYEAR = "endDateYear";
    public static final String STARTTIMEHOUR = "startTimeHour";
    public static final String STARTTIMEMINUTE = "startTimeMinute";
    public static final String ENDTIMEHOUR = "endTimeHour";
    public static final String ENDTIMEMINUTE = "endTimeMinute";
    public static final String DURATION = "duration";
    public static final String DESCRIPTION = "description";
    public static final String PERIODIC = "periodic";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title TEXT, startDateMonth INT, startDateDay INT, startDate startDayYear INT,startDateMonth INT, startDateDay INT, " +
                "endDateMonth INT, endMonthDay INT, endDayYear INT, startTimeHour INT, startTimeMinute INT, endTimeHour INT, endTimeMinute INT, " +
                "description TEXT, periodic INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.v("Constants",
                "Upgrading database which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Calendar");
        onCreate(db);
    }
}