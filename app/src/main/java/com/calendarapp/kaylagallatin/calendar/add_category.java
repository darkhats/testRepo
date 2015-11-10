package com.calendarapp.kaylagallatin.calendar;

import android.content.ContentValues;
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

public class add_category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button selectedSaveButton = (Button) this.findViewById(R.id.saveAddCategoryButton);
        selectedSaveButton.setText("Save");
        selectedSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                EditText editTextTitle = (EditText) findViewById(R.id.add_category_title_edit);
                values.put(DatabaseHelper.CATEGORYNAME,editTextTitle.getText().toString());
                long newRowId;
                newRowId = db.insert(
                        "categories",
                        null,
                        values);

                String Query = "SELECT * FROM categories";
                Cursor cur = db.rawQuery(Query,null);
                Log.d("cur size", " " + cur.getCount());
                finish();
            }
        });

    }

}
