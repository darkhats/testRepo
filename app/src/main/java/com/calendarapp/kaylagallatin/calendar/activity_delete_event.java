package com.calendarapp.kaylagallatin.calendar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_delete_event extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_delete_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button selectedEditButton = (Button) this.findViewById(R.id.saveDeleteEventButton);
        selectedEditButton.setText("Save");
        selectedEditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                String selectQuery = "SELECT title,description,location,category FROM events WHERE title = ?";
                EditText selectTextTitle = (EditText) findViewById(R.id.delete_event_title_edit);
                Cursor selectCur = db.rawQuery(selectQuery, new String[]{selectTextTitle.getText().toString()});
                if (selectCur.getCount() >= 1) {
                    selectCur.moveToFirst();
                    selectCur.close();
                    String Query = "DELETE FROM events WHERE title = ?";
                    EditText editTextTitle = (EditText) findViewById(R.id.delete_event_title_edit);
                    Log.d("event title", editTextTitle.getText().toString());
                    Cursor cur = db.rawQuery(Query, new String[]{editTextTitle.getText().toString()});
                    finish();
                } else {
                    Toast toast = Toast.makeText(activity_delete_event.this, "The event you're trying to delete does not exist! Please choose another ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

}
