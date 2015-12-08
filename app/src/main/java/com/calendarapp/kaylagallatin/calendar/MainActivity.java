package com.calendarapp.kaylagallatin.calendar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.graphics.Color;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final String tag = "MyCalendarActivity";

    private TextView currentMonth;
    private TextView selectedDayMonthYearButton;
    private Button addEventButton;
    private Button menuButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private Integer tempDayOfWeek;
    @SuppressLint("NewApi")
    private Integer month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";
    private Integer padding = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createHolidayDb();


        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        selectedDayMonthYearButton = (TextView) this.findViewById(R.id.selectedDayMonthYear);
        selectedDayMonthYearButton.setText("");

        addEventButton = (Button) this.findViewById(R.id.addEvent);
        addEventButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, add_event.class));
            }
        });

        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (TextView) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) this.findViewById(R.id.calendar);

        // Initialised
        adapter = new GridCellAdapter(getApplicationContext(),
                R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        menuButton = (Button) findViewById(R.id.menu);
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, menuButton);
                popup.getMenuInflater().inflate(R.menu.views_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Daily View")) {
                            startActivity(new Intent(MainActivity.this, daily_view.class));
                        } else if (item.getTitle().equals("Weekly View")) {
                            startActivity(new Intent(MainActivity.this, weekly_view.class));
                        }
                        else if(item.getTitle().equals("Delete Event"))
                            startActivity(new Intent(MainActivity.this, delete_event.class));
                        else if(item.getTitle().equals("Edit Event"))
                            startActivity(new Intent(MainActivity.this, choose_edit_event.class));
                        else if(item.getTitle().equals("Add Category"))
                            startActivity(new Intent(MainActivity.this, add_category.class));
                        else if(item.getTitle().equals("Delete Category"))
                            startActivity(new Intent(MainActivity.this, delete_category.class));
                        else {
                            Toast.makeText(
                                    MainActivity.this,
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


    private void createHolidayDb()
    {
        createHolidayDbHelper(25,12,"Christmas"); //Christmas
        createHolidayDbHelper(11,11,"Veteran's Day"); //Veteran's Day
        createHolidayDbHelper(14,2,"Valentine's Day"); //Valentine's Day
        createHolidayDbHelper(4,7,"USA Independence Day"); //Independence Day (USA)
        createHolidayDbHelper(31,10,"Halloween"); //Halloween
        createHolidayDbHelper(1,1,"New Year's Day"); //New Year's Day
    }

    private void createHolidayDbHelper(Integer day, Integer month, String name)
    {
        DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.HOLIDAYNAME,name);
        values.put(DatabaseHelper.HOLIDAYDAY,day.toString());
        values.put(DatabaseHelper.HOLIDAYMONTH,month.toString());
        long newRowId;
        newRowId = db.insert(
                "holidays",
                null,
                values);
    }

    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(),
                R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        selectedDayMonthYearButton.setText("");
        selectedDayMonthYearButton.setBackgroundColor(getResources().getColor(android.R.color.white));
        if (v == prevMonth) {
            if (month <= 1) { //If the current month is January, go back to December of the previous year
                month = 12;
                year--;
            } else {  //Else, just go back a month
                month--;
            }
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth) {
            if (month > 11) { //If the current month is December, go to January of the next year
                month = 1;
                year++;
            } else { //Else, just go forward a month
                month++;
            }
            setGridCellAdapterToDate(month, year);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // Inner Class
    public class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; //Array of the number of days in each month starting with January
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private Button gridcell;
        private TextView num_events_per_day;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("M/d/yyyy");

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

            // Print Month
            printMonth(month, year);
        }

        private String getMonthAsString(int i) {
            return "" + (i + 1);
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }


        private void printMonth(int mm, int yy) {
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
            tempDayOfWeek = cal.DAY_OF_WEEK;
            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

            // Trailing Month days (not in current Month)
            for (int i = 0; i < trailingSpaces; i++) {
                list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
            }

            // Current Month Days
            DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            for (int i = 1; i <= daysInMonth; i++) {
                String selectQuery = "SELECT * FROM events WHERE startDateDay = ? AND startDateMonth = ? AND startDateYear = ?";
                Cursor selectCur = db.rawQuery(selectQuery, new String[]{""+i,""+mm,""+yy});
                selectCur.moveToFirst();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                if (i == getCurrentDayOfMonth() && mm == month + 1 && yy == year) {
                    list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy); //Current day is highlighted
                }
                else if(selectCur.getCount() > 0)
                {
                    list.add(String.valueOf(i) + "-GREEN" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                }
                else {
                    list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy); //Other days of month
                }
                selectCur.close();
            }

            // Leading Month days (not in current Month)
            for (int i = 0; i < list.size() % 7; i++) {
                list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

            // Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setOnClickListener(this);

            // ACCOUNT FOR SPACING
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];


            // Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(themonth + "/" + theday + "/" + theyear);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources().getColor(R.color.lightgray));
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(getResources().getColor(R.color.lightgray02));
            }
            if (day_color[1].equals("BLUE")) {
                gridcell.setTextColor(getResources().getColor(R.color.orange));
            }
            if (day_color[1].equals("GREEN"))
            {
                gridcell.setTextColor(getResources().getColor(R.color.lightgreen));
            }

            return row;
        }

        @Override
        public void onClick(View view) { //When a calendar button is clicked

            calendarView.invalidateViews();
            String date_month_year = (String) view.getTag();
            String theseEvents = viewEvents(date_month_year);
            selectedDayMonthYearButton.setText(Html.fromHtml("Date: " + date_month_year + theseEvents), TextView.BufferType.SPANNABLE); //Displays the date and events on that day
            try {
                Date parsedDate = dateFormatter.parse(date_month_year);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public String viewEvents(String date) //View events on selected day
        {
            padding = 0; //Used for gridcell display issues
            TextView temp = (TextView)findViewById(R.id.selectedDayMonthYear);
            SQLiteDatabase db;
            DatabaseHelper mDbHelper = new DatabaseHelper(getApplicationContext());
            db = mDbHelper.getReadableDatabase();
            String data = " ";
            String Query = "SELECT * FROM events WHERE (dayofweek = ? AND dayofweek != ?) OR (startDateMonth = ? AND startDateDay = ? AND startDateYear = ?) ORDER BY startTimeHour, startTimeMinute";
            if(db != null) {

                String[] tempDate  = date.split("/");
                String day = tempDate[1];
                Calendar c = new GregorianCalendar();
                c.setFirstDayOfWeek(Calendar.SUNDAY);
                c.set(year, month-1, Integer.parseInt(day));
                c.setFirstDayOfWeek(Calendar.SUNDAY);
                tempDayOfWeek = c.get(_calendar.DAY_OF_WEEK);
                Cursor cur = db.rawQuery(Query, new String[]{tempDayOfWeek.toString(),"99",month.toString(), day, year.toString()});
                if(cur.getCount()>=2)
                    data += "*";
                data += "<br>";
                String selectQuery = "SELECT holidayName FROM holidays WHERE holidayDay = ? AND holidayMonth = ?";
                Cursor selectCur = db.rawQuery(selectQuery, new String[]{day.toString(),month.toString()});
                TextView tempDay = (TextView)findViewById(R.id.selectedDayMonthYear);
                tempDay.setBackgroundColor(getResources().getColor(android.R.color.white));
                if (selectCur.getCount() >= 1) {
                    selectCur.moveToFirst();
                    data += "Holiday: " + selectCur.getString(0) + "<br>";
                    TextView weeklyDay = (TextView) findViewById(R.id.selectedDayMonthYear);
                    weeklyDay.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                if(tempDayOfWeek == 1 || tempDayOfWeek == 7) {
                    TextView tempDay2 = (TextView) findViewById(R.id.selectedDayMonthYear);
                    tempDay2.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
                if(cur.getCount() >= 2) {
                 if( selectCur.getCount() > 0) {
                     padding += 200;
                 }
                    else {
                     padding += 100;
                 }
                }
                if (cur.getCount() >= 1) {
                    cur.moveToFirst();
                    int x = 1;
                    int tempCount = 0;
                    while (cur.isAfterLast() == false){
                        String QueryColor = "SELECT categoryColor FROM categories WHERE categoryName = ?";
                        Cursor curColor = db.rawQuery(QueryColor, new String[]{cur.getString(16)});
                        curColor.moveToFirst();
                        if(curColor.getCount() >= 1) //If it has a category, display its color
                        {
                            data += "<font color = '" + curColor.getString(0) + "'>";
                        }
                        x = 1;
                        data+= "Name: " + cur.getString(x) + "<br>";
                        x = 8;
                        data+= "Start Time: " + daily_view.formatTime(cur.getString(x++),cur.getString(x++)) + "<br>";
                        data+= "End Time: " + daily_view.formatTime(cur.getString(x++), cur.getString(x++)) + "<br>";
                        data+= "Location: " + cur.getString(x++) + "<br>";
                        data += "Description: " + cur.getString(x++) + "<br>";
                        x += 2;
                        data += "Category: " + cur.getString(x) + "<br><br><br>";
                        if(curColor.getCount() >= 1) //If it has a category, display its color
                        {
                            data += "</font>";
                        }
                        if(cur.getCount() >= 2 && tempCount++ > 0) //If multiple events, adjust padding
                        {
                            padding += 500;
                        }
                        cur.moveToNext();} //Get next item in select database
                }else
                {
                    data += "No events scheduled today <br>";
                }
            }
            temp.setPadding(0,padding,0,0);
            return data;
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }

    }//end GridCellAdapter

}//end MainActivity
