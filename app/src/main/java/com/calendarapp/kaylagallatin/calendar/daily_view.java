package com.calendarapp.kaylagallatin.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.Calendar;
import java.util.Locale;

public class daily_view extends AppCompatActivity implements OnClickListener{

    private Button menuButton;
    private Calendar calendar;
    private static final String dateTemplate = "c \nMMM d yyyy";
    private TextView currentDay;
    private ImageView prevDay;
    private ImageView nextDay;
    private int day, month, year;
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

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
        //Check for events on new day and setButtonText to event title
    }

}
