package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pradeep on 10/31/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Calendar.db";
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
    public static final String DESCRIPTION = "description";
    public static final String PERIODIC = "periodic";
    public static final String TABLENAME = "events";
    public static final String COLUMNID = "_id";
    public static final String LOCATION = "location";
    public static final String DAYOFWEEK = "dayofweek";
    private static final int DATABASE_VERSION = 87;
    protected static final String TABLENAME2 = "categories";
    protected static final String CATEGORYNAME = "categoryName";
    protected static final String CATEGORY = "category";

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
                ENDDATEMONTH + " INT," +
                ENDDATEDAY + " INT," +
                ENDDATEYEAR + " INT," +
                STARTTIMEHOUR + " INT," +
                STARTTIMEMINUTE + "  INT," +
                ENDTIMEHOUR + " INT," +
                ENDTIMEMINUTE + " INT," +
                LOCATION + " TEXT," +
                DESCRIPTION + " TEXT," +
                PERIODIC + " INT," +
                DAYOFWEEK + " INT," +
                CATEGORY + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLENAME2 + "(" + COLUMNID + " INTEGER PRIMARY KEY," +
                CATEGORYNAME + " TEXT)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.v("Constants",
                "Upgrading database which will destroy all old data");
       db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME2);
        onCreate(db);
    }
}