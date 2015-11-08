package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
=======
import android.view.View;
import android.widget.Button;
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

public class set_date extends AppCompatActivity {

    private Button cancelDate;
<<<<<<< HEAD
    private Button saveButton;
    public static DatePicker editTextStartDateMonth;
=======
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelDate = (Button) this.findViewById(R.id.cancelDateButton);
        cancelDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
<<<<<<< HEAD
                finish();
            }
        });

        saveButton = (Button) this.findViewById(R.id.saveDateButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editTextStartDateMonth = (DatePicker)findViewById(R.id.datePicker);
                Log.d("the date", editTextStartDateMonth.toString());
                finish();
=======
                startActivity(new Intent(set_date.this, add_event.class));
>>>>>>> 0135994114fb6601e02537281464cda23a3a24f8
            }
        });
    }

}
