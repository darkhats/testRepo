package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pradeep on 10/31/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
<<<<<<< HEAD
    public static final String DATABASE_NAME = "Calendar.db";
=======
    private static final String DATABASE_NAME = "Calendar.db";
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
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
<<<<<<< HEAD
    public static final String DESCRIPTION = "description";
    public static final String PERIODIC = "periodic";
    public static final String TABLENAME = "events";
    public static final String COLUMNID = "_id";
    public static final String LOCATION = "location";
    private static final int DATABASE_VERSION = 76;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" + COLUMNID + " INTEGER PRIMARY KEY," +
                TITLE + " TEXT," +
                STARTDATEMONTH + " INT," +
                STARTDATEDAY + " INT," +
                STARTDATEYEAR + " INT," +
                ENDDATEMONTH  + " INT," +
                ENDDATEDAY + " INT," +
                ENDDATEYEAR+ " INT," +
                STARTTIMEHOUR + " INT," +
                STARTTIMEMINUTE+ "  INT," +
                ENDTIMEHOUR + " INT," +
                ENDTIMEMINUTE + " INT," +
                LOCATION + " TEXT," +
                DESCRIPTION + " TEXT," +
                PERIODIC + " INT)");
=======
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
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.v("Constants",
                "Upgrading database which will destroy all old data");
<<<<<<< HEAD
        db.execSQL("DROP TABLE IF EXISTS events");
=======
        db.execSQL("DROP TABLE IF EXISTS Calendar");
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
        onCreate(db);
    }
}