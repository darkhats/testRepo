package com.calendarapp.kaylagallatin.calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

public class add_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView categoryDisplay = (TextView) findViewById(R.id.add_category_color_option_label);
        String text = "Category Color Options:<br><font color = 'Maroon'>maroon </font><font color = 'magenta'>magenta </font>" +
                "<font color = 'purple'>purple </font><font color = 'red'>red</font>";
        categoryDisplay.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        Button selectedSaveButton = (Button) this.findViewById(R.id.saveAddCategoryButton);
        selectedSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] colors = {"magenta","red","purple","maroon"};
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                EditText editTextTitle = (EditText) findViewById(R.id.add_category_title_edit);
                EditText editTextColor = (EditText) findViewById(R.id.add_category_color_edit);
                boolean inColors = false;
                for(String x: colors)
                    inColors = inColors || x.equals(editTextColor.getText().toString());
                if(inColors) {
                    values.put(DatabaseHelper.CATEGORYNAME, editTextTitle.getText().toString());
                    values.put(DatabaseHelper.CATEGORYCOLOR, editTextColor.getText().toString());
                    long newRowId;
                    newRowId = db.insert(
                            "categories",
                            null,
                            values);

                    finish();
                }
                else
                {
                    Toast toast = Toast.makeText(add_category.this, "Invalid color. Please type a color listed ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }



}
