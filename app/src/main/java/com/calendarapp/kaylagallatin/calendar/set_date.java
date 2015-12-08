package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.view.View;
import android.widget.Button;

public class set_date extends AppCompatActivity {

    private Button cancelDate;
    private Button saveButton;
    public static DatePicker editTextStartDateMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelDate = (Button) this.findViewById(R.id.cancelDateButton);
        cancelDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
            }
        });

        saveButton = (Button) this.findViewById(R.id.saveDateButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextStartDateMonth = (DatePicker)findViewById(R.id.datePicker); //Saves date set
                finish();

            }
        });
    }

}
