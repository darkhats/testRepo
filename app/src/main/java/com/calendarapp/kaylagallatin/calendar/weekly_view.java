package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

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
    private int day, month, year;
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private List<String> list;
    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grid = (GridView) findViewById(R.id.calendar);

        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
        prevWeek.setOnClickListener(this);

        calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        //Sets calendar to first day of the week
        setCalWeekDay(calendar);
        list = new ArrayList<String>();

        currentWeek = (TextView) this.findViewById(R.id.currentWeek);

        nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
        nextWeek.setOnClickListener(this);

        Button eventButton = (Button) this.findViewById(R.id.view_event_button);
        setButtonText(eventButton, "Event Name Here");
        eventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(weekly_view.this, view_event.class));
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
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(weekly_view.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.views_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Daily View")) {
                            startActivity(new Intent(weekly_view.this, daily_view.class));
                        } else if (item.getTitle().equals("Monthly View")) {
                            startActivity(new Intent(weekly_view.this, MainActivity.class));
                        } else {
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

    private void setButtonText(Button eventButton, String text){ eventButton.setText(text);}

    public void setCalWeekDay(Calendar calendar){
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        while(dayOfWeek > 1){
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
        if (v == prevWeek) {
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
        }else if (v == nextWeek) {
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
    }

    private void printWeek(Calendar calendar) {
        list.clear();
        CharSequence firstDay = DateFormat.format(dateTemplate, calendar.getTime());
        int cDay = calendar.get(Calendar.DAY_OF_MONTH);
        int cMonth = calendar.get(Calendar.MONTH);
        int cYear = calendar.get(Calendar.YEAR);
        list.add("\t\t" + String.valueOf(day));

        // Add Current Week Days
        for (int i = 1; i <= 6; i++) {
            incrementDay(calendar);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            list.add("\t\t" + String.valueOf(dayOfMonth));
        }
        CharSequence lastDay = DateFormat.format(dateTemplate, calendar.getTime());
        currentWeek.setText(String.valueOf(firstDay) + " -\n" + String.valueOf(lastDay));
        calendar.set(cYear, cMonth, cDay);
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
        grid.setAdapter(adp);
    }

    private void incrementDay(Calendar calendar){
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
        calendar.set(year, month, day);
    }
}
