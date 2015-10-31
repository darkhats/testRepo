package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class set_time extends AppCompatActivity {

    private Button cancelTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelTime = (Button) this.findViewById(R.id.cancelTimeButton);
        cancelTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(set_time.this, add_event.class));
            }
        });
    }

}
