package com.calendarapp.kaylagallatin.calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class weekly_view extends AppCompatActivity implements OnClickListener{

    private Button menuButton;
    private Calendar calendar;
    private static final String dateTemplate = "MMM d yyyy";
    private TextView currentWeek;
    private ImageView prevWeek;
    private ImageView nextWeek;
    private Button button;
    private int day, month, year;
    private final int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private List<String> list;
    private GridView grid;
    private Integer cDay;
    private Integer cMonth;
    private Integer cYear;
    private Button sun;
    private Button mon;
    private Button tue;
    private Button wen;
    private Button thu;
    private Button fri;
    private Button sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grid = (GridView) findViewById(R.id.calendar);



        calendar = Calendar.getInstance(Locale.getDefault());
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        //Sets calendar to first day of the week
        setCalWeekDay(calendar);
        list = new ArrayList<String>();

        currentWeek = (TextView) this.findViewById(R.id.currentWeek);

        nextWeek = (ImageView) this.findViewById(R.id.nextWeek);
        nextWeek.setOnClickListener(this);
        prevWeek = (ImageView) this.findViewById(R.id.prevWeek);
        prevWeek.setOnClickListener(this);
  //      button = (Button) this.findViewById(R.id.saveAddEventButton);
  //      button.setOnClickListener(this);

  //      Button eventButton = (Button) this.findViewById(R.id.view_event_button);
    //    setButtonText(eventButton, "Event Name Here");
      //  eventButton.setOnClickListener(new View.OnClickListener() {
       //     public void onClick(View v) {
        //        startActivity(new Intent(weekly_view.this, view_event.class));
          //  }
       // });


        sun = (Button) this.findViewById(R.id.sunday);
        sun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(0);
            }
        });

        mon = (Button) this.findViewById(R.id.monday);
        mon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(1);
            }
        });

        tue = (Button) this.findViewById(R.id.tuesday);
        tue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(2);
            }
        });

        wen = (Button) this.findViewById(R.id.wednesday);
        wen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(3);
            }
        });

        thu = (Button) this.findViewById(R.id.thursday);
        thu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(4);
            }
        });
        fri = (Button) this.findViewById(R.id.friday);
        fri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                viewEvents(5);
            }
        });
        sat = (Button) this.findViewById(R.id.saturday);
        sat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewEvents(6);
            }
        });
        Button addEventButton = (Button) this.findViewById(R.id.addEvent);
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

        printWeek(calendar);
    }

  //  private void setButtonText(Button eventButton, String text){ eventButton.setText(text);}

    public void viewEvents(int dayofWeekNumber)
    {
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Calendar temp = new GregorianCalendar(cYear,cMonth,cDay);
        for(int k = 0; k < dayofWeekNumber; k++)
            incrementDay(temp,temp.get(Calendar.DAY_OF_MONTH),temp.get(Calendar.MONTH),temp.get(Calendar.YEAR));
        Integer day = temp.get(Calendar.DAY_OF_MONTH);
        Integer month = temp.get(Calendar.MONTH) + 1;
        Integer year = temp.get(Calendar.YEAR);
        Integer dayOfWeek = temp.get(Calendar.DAY_OF_WEEK) % 7;
        Integer tempMonth = cMonth + 1;
        Log.d("you"," clicked sunday" + cDay + " " + cMonth);
        String Query = "SELECT * FROM events WHERE (dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?) ORDER BY startTimeHour, startTimeMinute";
        Cursor cur = db.rawQuery(Query, new String[]{dayOfWeek.toString(),"99",month.toString(), day.toString(), year.toString()});
        cur.moveToFirst();
        TextView eventList = (TextView)findViewById(R.id.weeklyEditText);
        String data = "Selected Date: " + (month) + "/" + day + "/" + year + "\n\n";

        if(cur.getCount() > 0)
        {
            data += "Events\n";
            while(cur.isAfterLast() == false)
            {
                data += "Event name: " + cur.getString(1) + "\n";
                cur.moveToNext();
            }

        }
        eventList.setText(data);
    }

    public void setCalWeekDay(Calendar calendar){
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        while(dayOfWeek > 1){
            Log.d("day in setCalWeeklyDay is ", "" + day);
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
            dayOfWeek--;
        }
        calendar.set(year, month, day);

    }

    @Override
    public void onClick(View v) {
        Log.d("View",v.toString());
        TextView eventList = (TextView)findViewById(R.id.weeklyEditText);
        eventList.setText("");
        if (v == prevWeek) {
            Log.d("the day is ", "" + day + " " + calendar.get(calendar.DAY_OF_WEEK));
            if(month == 0 && day < 8) {
                year--;
                month = 11;
                day = 31;
            }
          else if(day < 8){
                month--;
                day = monthDays[month] - 7 + day;
                Log.d("the post day is ", "" + day);
            }
        else if(day >= 8){
                day-= 7;
            }
        }else if (v == nextWeek) {
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
        else if(v == button)
        {
            Button temp = (Button) this.findViewById(R.id.saveAddEventButton);
            temp.setText("hllo");
        }
        calendar.set(year, month, day);
        //get first day of the week
        setCalWeekDay(calendar);
        printWeek(calendar);
        //Check for events on new day and setButtonText to event title
    }

    private void printWeek(Calendar calendar) {
        list.clear();
        CharSequence firstDay = DateFormat.format(dateTemplate, calendar.getTime());
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        cMonth = calendar.get(Calendar.MONTH);
        cYear = calendar.get(Calendar.YEAR);
//        list.add("\t\t" + String.valueOf(day));

//        // Add Current Week Days
//        for (int i = 1; i <= 6; i++) {
//            incrementDay(calendar);
//            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//            list.add("\t\t" + String.valueOf(dayOfMonth) + "HELLO WORLD!!!!!!!!");
//        }
//        CharSequence lastDay = DateFormat.format(dateTemplate, calendar.getTime());
//        currentWeek.setText(String.valueOf(firstDay) + " -\n" + String.valueOf(lastDay));
        calendar.set(cYear, cMonth, cDay);
//        ArrayAdapter<String> adp=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,list);
////        grid.setAdapter(adp)
        CharSequence lastDay = "";
        for(int k = 0; k < 7; k++) {
            Integer dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (k == 0) {
                Button temp = (Button) findViewById(R.id.sunday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 1) {
                Button temp = (Button) findViewById(R.id.monday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 2) {
                Button temp = (Button) findViewById(R.id.tuesday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 3) {
                Button temp = (Button) findViewById(R.id.wednesday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 4) {
                Button temp = (Button) findViewById(R.id.thursday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 5) {
                Button temp = (Button) findViewById(R.id.friday);
                temp.setText(dayofMonth.toString());
            }
            if (k == 6) {
                Button temp = (Button) findViewById(R.id.saturday);
                temp.setText(dayofMonth.toString());
                lastDay = DateFormat.format(dateTemplate, calendar.getTime());
            }
            if (k != 6)
                incrementDay(calendar);
        }

        currentWeek.setText(String.valueOf(firstDay) + " -\n" + String.valueOf(lastDay));
    }



    private void incrementDay(Calendar calendar){
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
        calendar.set(year, month, day);
    }

    private void incrementDay(Calendar calendar, int tempDay, int tempMonth, int tempYear){
        if(tempMonth == 11 && tempDay == 31){
            tempYear++;
            tempMonth = 0;
            tempDay = 1;
        }else if(monthDays[tempMonth] == tempDay) {
            tempMonth++;
            tempDay = 1;
        } else {
            tempDay++;
        }
        calendar.set(tempYear, tempMonth, tempDay);
    }
}
