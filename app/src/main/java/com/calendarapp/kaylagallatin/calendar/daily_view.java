package com.calendarapp.kaylagallatin.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    public String data;
    private GregorianCalendar tempCal;




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
        tempCal = new GregorianCalendar(year,month,day);
        makeView();
        currentDay = (TextView) this.findViewById(R.id.currentDay);
        currentDay.setText(DateFormat.format(dateTemplate, calendar.getTime()));

        nextDay = (ImageView) this.findViewById(R.id.nextDay);
        nextDay.setOnClickListener(this);

        Button eventButton = (Button) this.findViewById(R.id.view_event_button);
        setButtonText(eventButton, "Temp Placeholder");
        eventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(daily_view.this, view_event.class));
            }
        });

        Button addEventButton = (Button) this.findViewById(R.id.addEvent);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(daily_view.this, add_event.class));
            }
        });

        menuButton = (Button) findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(daily_view.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.views_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Monthly View")) {
                            startActivity(new Intent(daily_view.this, MainActivity.class));
                        } else if (item.getTitle().equals("Weekly View")) {
                            startActivity(new Intent(daily_view.this, weekly_view.class));
                        } else {
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

    }

    private void setButtonText(Button eventButton, String text){
        eventButton.setText(text);
    }

    @Override
    public void onClick(View v) {
        if (v == prevDay) {
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
        }else if (v == nextDay) {
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
        makeView();
        //Check for events on new day and setButtonText to event title
    }

    public void makeView()
    {
        SQLiteDatabase db;
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        db = mDbHelper.getWritableDatabase();
        data = "";
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        Integer tempMonth = month + 1;
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(year, month, day);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        Integer tempDayOfWeek = c.get(calendar.DAY_OF_WEEK);
        Log.d("day of the week ","" + tempDayOfWeek + "day:" + day);
        String Query = "SELECT * FROM events WHERE (dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?) ORDER BY startTimeHour, startTimeMinute";
        if(db != null) {
            Log.d("i'm"," here");
            Cursor cur = db.rawQuery(Query, new String[]{tempDayOfWeek.toString(),"99",tempMonth.toString(), day.toString(), year.toString()});
            Log.d("count", "" + cur.getCount());
            if (cur.getCount() >= 1) {
                cur.moveToFirst();
                int x = 1;
                while (cur.isAfterLast() == false){
                    x = 1;
                    data+= "Event Name: " + cur.getString(x) + "k\n";
                    x = 8;
                    data+= "Event Start Time: " + cur.getString(x++) + ":" + cur.getString(x++) + "\n";
                    data+= "Event End Time: " + cur.getString(x++) + ":" + cur.getString(x++) + "\n";
                    data+= "Event Location: " + cur.getString(x++) + "\n";
                    data += "Event Description: " + cur.getString(x++) + "\n\n\n";
                    cur.moveToNext();}
            }

        }
        TextView temp = (TextView)findViewById(R.id.textViewDaily);
        temp.setText(data);

    }



}
