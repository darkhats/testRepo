package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class weekly_view extends AppCompatActivity implements OnClickListener{

    private Button menuButton;
    private Calendar calendar;
    private static final String dateTemplate = "MMM d yyyy";
    private TextView currentWeek;
    private ImageView prevWeek;
    private ImageView nextWeek;
    private Button button;
    private int day, month, year;
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private Integer cDay;
    private Integer cMonth;
    private Integer cYear;
    private Button sun;
    private Button mon;
    private Button tue;
    private Button wen;
    private Button thu;
    private Button fri;
    private Button sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        //Sets calendar to first day of the week
        setCalWeekDay(calendar);

        currentWeek = (TextView) this.findViewById(R.id.currentWeek);
        nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
        nextWeek.setOnClickListener(this);
        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
        prevWeek.setOnClickListener(this);

        sun = (Button) this.findViewById(R.id.sunday);
        sun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(0);
            }
        });
        mon = (Button) this.findViewById(R.id.monday);
        mon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(1);
            }
        });
        tue = (Button) this.findViewById(R.id.tuesday);
        tue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(2);
            }
        });
        wen = (Button) this.findViewById(R.id.wednesday);
        wen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(3);
            }
        });
        thu = (Button) this.findViewById(R.id.thursday);
        thu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(4);
            }
        });
        fri = (Button) this.findViewById(R.id.friday);
        fri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(5);
            }
        });
        sat = (Button) this.findViewById(R.id.saturday);
        sat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(6);
            }
        });

        Button addEventButton = (Button) this.findViewById(R.id.addEvent);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(weekly_view.this, add_event.class));
            }
        });

        menuButton = (Button) findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Sets menu option functionality
                PopupMenu popup = new PopupMenu(weekly_view.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.views_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Daily View")) {
                            startActivity(new Intent(weekly_view.this, daily_view.class));
                        } else if (item.getTitle().equals("Monthly View")) {
                            startActivity(new Intent(weekly_view.this, MainActivity.class));
                        }
                        else if(item.getTitle().equals("Delete Event"))
                            startActivity(new Intent(weekly_view.this, delete_event.class));
                        else if(item.getTitle().equals("Edit Event"))
                            startActivity(new Intent(weekly_view.this, choose_edit_event.class));
                        else if(item.getTitle().equals("Add Category"))
                            startActivity(new Intent(weekly_view.this, add_category.class));
                        else if(item.getTitle().equals("Delete Category"))
                            startActivity(new Intent(weekly_view.this, delete_category.class));
                        else {
                            Toast.makeText(
                                    weekly_view.this,
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
        printWeek(calendar);
    }

    public void viewEvents(int dayofWeekNumber) //View events for day selected
    {
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Calendar temp = new GregorianCalendar(cYear,cMonth,cDay);
        for(int k = 0; k < dayofWeekNumber; k++)
            incrementDay(temp,temp.get(Calendar.DAY_OF_MONTH),temp.get(Calendar.MONTH),temp.get(Calendar.YEAR));
        Integer day = temp.get(Calendar.DAY_OF_MONTH);
        Integer month = temp.get(Calendar.MONTH) + 1;
        Integer year = temp.get(Calendar.YEAR);
        Integer dayOfWeek = temp.get(Calendar.DAY_OF_WEEK) % 7;
        Integer tempMonth = cMonth + 1;
        String Query = "SELECT * FROM events WHERE (dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?) ORDER BY startTimeHour, startTimeMinute";
        Cursor cur = db.rawQuery(Query, new String[]{dayOfWeek.toString(),"99",month.toString(), day.toString(), year.toString()});
        cur.moveToFirst();
        TextView eventList = (TextView)findViewById(R.id.weeklyEditText);
        String data = "Date: " + (month) + "/" + day + "/" + year + "<br>";
        String selectQuery = "SELECT holidayName FROM holidays WHERE holidayDay = ? AND holidayMonth = ?";
        Cursor selectCur = db.rawQuery(selectQuery, new String[]{day.toString(),tempMonth.toString()});
        TextView tempDay = (TextView)findViewById(R.id.weeklyEditText);
        tempDay.setBackgroundColor(getResources().getColor(android.R.color.white));
        if (selectCur.getCount() >= 1) { //If any holidays occur on this day, change the background color to green
            selectCur.moveToFirst();
            data += "Holiday: " + selectCur.getString(0) + "<br>";
            TextView weeklyDay = (TextView) findViewById(R.id.weeklyEditText);
            weeklyDay.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        }
        if(dayOfWeek == 0 || dayOfWeek == 1) { //If it's a weekend, set the color to blue
            TextView tempDay2 = (TextView) findViewById(R.id.weeklyEditText);
            tempDay2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        if(cur.getCount() > 0) //If there are events on the day selected, display them
        {
            cur.moveToFirst();
            data += "<br><br>";
            int x = 0;
            while(!cur.isAfterLast())
            {
                String QueryColor = "SELECT categoryColor FROM categories WHERE categoryName = ?";
                Cursor curColor = db.rawQuery(QueryColor, new String[]{cur.getString(16)});
                curColor.moveToFirst();
                if(curColor.getCount() >= 1) { //If the event has a category, display it in its color
                    data += "<font color = '" + curColor.getString(0) + "'>";
                }
                    x = 1;
                    data+= "Name: " + cur.getString(x) + "<br>";
                    x = 8;
                    data+= "Start Time: " + daily_view.formatTime(cur.getString(x++),cur.getString(x++)) + "<br>";
                    data+= "End Time: " + daily_view.formatTime(cur.getString(x++),cur.getString(x++)) + "<br>";
                    data+= "Location: " + cur.getString(x++) + "<br>";
                    data += "Description: " + cur.getString(x++) + "<br>";
                    x += 2;
                    data += "Category: " + cur.getString(x) + "<br><br><br>";
                if(curColor.getCount() >= 1) {
                    data += "</font>";
                }
                cur.moveToNext();
            }

        }else{
            data += "No events scheduled today <br>";
        }
        eventList.setText(Html.fromHtml(data), TextView.BufferType.SPANNABLE); //Show events
    }

    public void setCalWeekDay(Calendar calendar){
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        while(dayOfWeek > 1){ //Move to beginning of week
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
            dayOfWeek--;
        }
        calendar.set(year, month, day);

    }

    @Override
    public void onClick(View v) {
        TextView eventList = (TextView)findViewById(R.id.weeklyEditText);
        eventList.setText("");
        if (v == prevWeek) { //If going back a week, change view to previous week
            if(month == 0 && day < 8) {
                year--;
                month = 11;
                day = 31 - 7 + day;
            }
            else if(day < 8){
                month--;
                day = monthDays[month] - 7 + day;
            }
            else if(day >= 8){
                day-= 7;
            }
        }else if (v == nextWeek) { //If going forward a week, change view to next week
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
        //get first day of the week
        setCalWeekDay(calendar);
        printWeek(calendar);
        //Check for events on new day and setButtonText to event title
        TextView tempDay = (TextView)findViewById(R.id.weeklyEditText);
        tempDay.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private void printWeek(Calendar calendar) {
        CharSequence firstDay = DateFormat.format(dateTemplate, calendar.getTime());
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        cMonth = calendar.get(Calendar.MONTH);
        cYear = calendar.get(Calendar.YEAR);
        CharSequence lastDay = "";
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM events WHERE startDateDay = ? AND startDateMonth = ? AND startDateYear = ?";
        String holidayQuery = "SELECT holidayName FROM holidays WHERE holidayDay = ? AND holidayMonth = ?";
        for(int k = 0; k < 7; k++) { //k == day of week, where 0 = sunday, 1 = monday etc.
            Integer dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
            Cursor selectCur = db.rawQuery(selectQuery, new String[]{"" + dayofMonth, "" + (cMonth + 1), "" + cYear});
            selectCur.moveToFirst();
            Cursor holidayCur = db.rawQuery(holidayQuery, new String[]{"" + dayofMonth, "" + (cMonth + 1)});
            holidayCur.moveToFirst();
            Button temp;
            if (k == 0) {
                temp = (Button) findViewById(R.id.sunday);
                temp.setText(dayofMonth.toString());
                temp.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            }
            else if (k == 1) {
                temp = (Button) findViewById(R.id.monday);
                temp.setText(dayofMonth.toString());
            }
            else if (k == 2) {
                temp = (Button) findViewById(R.id.tuesday);
                temp.setText(dayofMonth.toString());
            }
            else if (k == 3) {
                temp = (Button) findViewById(R.id.wednesday);
                temp.setText(dayofMonth.toString());
            }
            else if (k == 4) {
                temp = (Button) findViewById(R.id.thursday);
                temp.setText(dayofMonth.toString());
            }
            else if (k == 5) {
                temp = (Button) findViewById(R.id.friday);
                temp.setText(dayofMonth.toString());
            }
            else {
                temp = (Button) findViewById(R.id.saturday);
                temp.setText(dayofMonth.toString());
                temp.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                lastDay = DateFormat.format(dateTemplate, calendar.getTime());
            }
            if (k != 6)
                incrementDay(calendar);
            temp.setTextColor(getResources().getColor(android.R.color.black));
            if (selectCur.getCount() > 0) //If there are events scheduled, change day color
            {
                temp.setTextColor(getResources().getColor(android.R.color.holo_purple));
            }
            if (holidayCur.getCount() > 0) //If there is a holiday, set the button for that day to green
                temp.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            if(holidayCur.getCount() == 0 && k != 6 && k != 0) //If the day is not a holiday nor a weekend, set button color to grey
                temp.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            selectCur.close();
            holidayCur.close();
        }

        currentWeek.setText(String.valueOf(firstDay) + " -\n" + String.valueOf(lastDay)); //Sets week view at top
    }

    private void incrementDay(Calendar calendar){ //move day in year
        if(month == 11 && day == 31){ //if it's the last day of the year
            year++;
            month = 0;
            day = 1;
        }else if(monthDays[month] == day) {
            month++;
            day = 1;
        } else {
            day++;
        }
        calendar.set(year, month, day);
    }

    private void incrementDay(Calendar calendar, int tempDay, int tempMonth, int tempYear){ //move day in year given local variables
        if(tempMonth == 11 && tempDay == 31){
            tempYear++;
            tempMonth = 0;
            tempDay = 1;
        }else if(monthDays[tempMonth] == tempDay) {
            tempMonth++;
            tempDay = 1;
        } else {
            tempDay++;
        }
        calendar.set(tempYear, tempMonth, tempDay);
    }
}