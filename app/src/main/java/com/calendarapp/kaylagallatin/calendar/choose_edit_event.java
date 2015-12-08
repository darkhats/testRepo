package com.calendarapp.kaylagallatin.calendar;
//Used to choose the event to edit
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


public class choose_edit_event extends AppCompatActivity {
    protected static String title = "";
    protected static String description = "";
    protected static String location = "";
    protected static String category = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_edit_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button selectedEditButton = (Button) this.findViewById(R.id.confirmSelectEventButton);
        selectedEditButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                String selectQuery = "SELECT title,description,location,category FROM events WHERE title = ?";
                EditText selectTextTitle = (EditText) findViewById(R.id.choose_edit_event_title_edit);
                Cursor selectCur = db.rawQuery(selectQuery, new String[]{selectTextTitle.getText().toString()});
                if(selectCur.getCount() >= 1) { //If the event is in the database, populate the edit screen with the record's info and move to edit event screen
                    selectCur.moveToFirst();
                    title = selectCur.getString(0);
                    description = selectCur.getString(1);
                    location = selectCur.getString(2);
                    category = selectCur.getString(3);
                    selectCur.close();
                    String Query = "DELETE FROM events WHERE title = ?";
                    Cursor cur = db.rawQuery(Query, new String[]{selectTextTitle.getText().toString()}); //Deletes the old event from the database
                    Log.d("cur size edit ",""+cur.getCount()); //DON'T REMOVE
                    cur.close();
                    startActivity(new Intent(choose_edit_event.this, add_event.class));
                    finish();
                }
                else
                {
                    Toast toast = Toast.makeText(choose_edit_event.this, "The event you're trying to edit does not exist! Please choose another " , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
