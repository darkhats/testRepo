package com.calendarapp.kaylagallatin.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.util.Log;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class daily_view extends AppCompatActivity implements OnClickListener{

    private Button menuButton;
    private Calendar calendar;
    private static final String dateTemplate = "c \nMMM d yyyy";
    private TextView currentDay;
    private ImageView prevDay;
    private ImageView nextDay;
    private Integer day, month, year;
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };//Array of the number of days in each month starting with January
    public String data = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prevDay = (ImageView) this.findViewById(R.id.prevDay);
        prevDay.setOnClickListener(this);

        calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        makeView();
        currentDay = (TextView) this.findViewById(R.id.currentDay);
        currentDay.setText(DateFormat.format(dateTemplate, calendar.getTime()));

        nextDay = (ImageView) this.findViewById(R.id.nextDay);
        nextDay.setOnClickListener(this);

        Button addEventButton = (Button) this.findViewById(R.id.addEvent);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(daily_view.this, add_event.class));
            }
        });
        //Adds functionality to top left button
        menuButton = (Button) findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Gives the menu button functionality
                PopupMenu popup = new PopupMenu(daily_view.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.views_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Monthly View")) {
                            startActivity(new Intent(daily_view.this, MainActivity.class));
                        } else if (item.getTitle().equals("Weekly View")) {
                            startActivity(new Intent(daily_view.this, weekly_view.class));
                        }
                        else if(item.getTitle().equals("Delete Event"))
                            startActivity(new Intent(daily_view.this, delete_event.class));
                        else if(item.getTitle().equals("Edit Event"))
                            startActivity(new Intent(daily_view.this, choose_edit_event.class));
                        else if(item.getTitle().equals("Add Category"))
                            startActivity(new Intent(daily_view.this, add_category.class));
                        else if(item.getTitle().equals("Delete Category"))
                            startActivity(new Intent(daily_view.this, delete_category.class));
                        else {
                            Toast.makeText(
                                    daily_view.this,
                                    "Already showing " + item.getTitle(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectQuery = "SELECT holidayName FROM holidays WHERE holidayDay = ? AND holidayMonth = ?";
        Cursor selectCur = db.rawQuery(selectQuery, new String[]{day.toString(),month.toString()});
        TextView tempDay = (TextView)findViewById(R.id.textViewDaily);
        tempDay.setBackgroundColor(getResources().getColor(android.R.color.white));
        if (selectCur.getCount() >= 1) { //If today is a holiday, display the holiday and set the background color to green
            selectCur.moveToFirst();
            data += "Holiday: " + selectCur.getString(0) + "<br>";
            TextView day = (TextView)findViewById(R.id.textViewDaily);
            day.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

        }

    }

    private void setButtonText(Button eventButton, String text){
        eventButton.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v == prevDay) { //If we are going back a day, go to the previous day
            if(month == 0 && day == 1){
                year--;
                month = 11;
                day = 31;
            }else if(day == 1){
                month--;
                day = monthDays[month];
            }else{
                day--;
            }
        }else if (v == nextDay) { //If we are going forward a day, go to the next day
            if(month == 11 && day == 31){
                year++;
                month = 0;
                day = 1;
            }else if(monthDays[month] == day) {
                month++;
                day = 1;
            } else {
                day++;
            }
        }
        calendar.set(year, month, day);
        currentDay.setText(DateFormat.format(dateTemplate, calendar.getTime()));
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectQuery = "SELECT holidayName FROM holidays WHERE holidayDay = ? AND holidayMonth = ?";
        Integer tempMonth = month + 1;
        Cursor selectCur = db.rawQuery(selectQuery, new String[]{day.toString(),tempMonth.toString()});
        TextView tempDay = (TextView)findViewById(R.id.textViewDaily);
        tempDay.setBackgroundColor(getResources().getColor(android.R.color.white));
        if (selectCur.getCount() >= 1) { //If today is a holiday, display the holiday and set the background color to green
            selectCur.moveToFirst();
            data += "Holiday: " + selectCur.getString(0) + "<br>";
            TextView day = (TextView)findViewById(R.id.textViewDaily);
            day.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        makeView();
        //Check for events on new day and setButtonText to event title
    }

    public void makeView()
    {
        SQLiteDatabase db;
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        db = mDbHelper.getWritableDatabase();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        Integer tempMonth = month + 1;
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(year, month, day);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        Integer tempDayOfWeek = c.get(calendar.DAY_OF_WEEK);
        Log.d("day of the week ", "" + tempDayOfWeek + "day:" + day);
        if(tempDayOfWeek == 1 || tempDayOfWeek == 7)
        {
            TextView tempDay2 = (TextView)findViewById(R.id.textViewDaily);
            tempDay2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        String Query = "SELECT * FROM events WHERE (dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?) ORDER BY startTimeHour, startTimeMinute";
        if(db != null) {
            Cursor cur = db.rawQuery(Query, new String[]{tempDayOfWeek.toString(),"99",tempMonth.toString(), day.toString(), year.toString()});
            if (cur.getCount() >= 1) { //If there is an event on this day
                cur.moveToFirst();
                int x = 1;
                while (cur.isAfterLast() == false){ //While we have not displayed all of the events for this day
                    String QueryColor = "SELECT categoryColor FROM categories WHERE categoryName = ?";
                    Cursor curColor = db.rawQuery(QueryColor, new String[]{cur.getString(16)});
                    curColor.moveToFirst();
                    if(curColor.getCount() >= 1) //If the event has a category, display it in its color
                        data += "<font color = '" + curColor.getString(0) + "'>";
                    x = 1;
                    data+= "Name: " + cur.getString(x) + "<br>";
                    x = 8;
                    data+= "Start Time: " + formatTime(cur.getString(x++),cur.getString(x++)) + "<br>";
                    data+= "End Time: " + formatTime(cur.getString(x++),cur.getString(x++)) + "<br>";
                    data+= "Location: " + cur.getString(x++) + "<br>";
                    data += "Description: " + cur.getString(x++) + "<br>";
                    x += 2;
                    data += "Category: " + cur.getString(x) + "<br><br><br>";
                    if(curColor.getCount() >= 1)
                        data += "</font>";
                    cur.moveToNext();}
            }else{
                data += "No events scheduled today <br>";
            }

        }
        TextView temp = (TextView)findViewById(R.id.textViewDaily);
        temp.setText(Html.fromHtml(data), TextView.BufferType.SPANNABLE);
        data = " ";
    }

    protected static String formatTime(String hour, String minute) //Changes 24 hour clock time to a 12 hour clock time
    {
        String amOrPm = " AM";
        String time = "";
        if(Integer.parseInt(hour) >= 12) {
            amOrPm = " PM";
            Integer tempHour = (Integer.parseInt(hour) == 12 ? 12:Integer.parseInt(hour) % 12);
            time = tempHour.toString();
        }
        else if(Integer.parseInt(hour) == 0)
            time = "12";
        else
            time = hour;
        if (minute.length() == 1)
            time += ":0" + minute;
        else
            time += ":" + minute;
        return time + amOrPm;

    }

}