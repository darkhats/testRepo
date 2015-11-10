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

public class delete_category extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button selectedSaveButton = (Button) this.findViewById(R.id.saveDeleteCategoryButton);
        selectedSaveButton.setText("Confirm");
        selectedSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                EditText editTextTitle = (EditText) findViewById(R.id.delete_category_title_edit);
                String Query = "DELETE FROM categories WHERE categoryName = ?";
                Cursor cur = db.rawQuery(Query, new String[]{editTextTitle.getText().toString()});
                String Query3 = "Select * FROM events WHERE category = ?";
                Cursor cur3 = db.rawQuery(Query3, new String[]{editTextTitle.getText().toString()});
                Log.d("cur3",""+cur3.getCount());
                String Query2 = "UPDATE events SET category = ? WHERE category = ?";
                Cursor cur2 = db.rawQuery(Query2, new String[]{"",editTextTitle.getText().toString()});
                Log.d("cur2",""+cur2.getCount());
                Log.d("cat",editTextTitle.getText().toString());
                finish();
            }
        });

    }

}
