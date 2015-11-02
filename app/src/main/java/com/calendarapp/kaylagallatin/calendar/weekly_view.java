package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class weekly_view extends AppCompatActivity {

    private Button menuButton;
    private Button selectedDayMonthYearButton;
    private Button addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedDayMonthYearButton = (Button) this.findViewById(R.id.selectedDayMonthYear);
        selectedDayMonthYearButton.setText("");

        addEventButton = (Button) this.findViewById(R.id.addEvent);
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
    }

}
