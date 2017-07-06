package com.holidayme.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.holidayme.AppInterface.DateSelectListener;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.data.DateData;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.holidayme.activities.R.*;

public class CheckInDialogView {

    private TextView currentMonth;
    private ImageView prevMonth,nextMonth;
    private GridView calendarView;
    private LinearLayout calendarLayout;
    private GridCellAdapter adapter;
    private Calendar calendar;
    private int month, year,current_month, current_year, current_day;
    private Context context;
    private String language,defaultLanguage;
    private DateSelectListener dateSelectListener;
    private ArrayList<DateData> selectedDatesArrayList;
    private Locale locale;
    private ArrayList<String> monthsNameArrayList = new ArrayList<>();
    boolean isIndex;

    public CheckInDialogView(Context context, String language, ArrayList<DateData> selectedDates, DateSelectListener dateSelectListener, String default_language,Boolean isIndex ) {
        this.dateSelectListener = dateSelectListener;
        this.context = context;
        this.language = language;
        this.selectedDatesArrayList = selectedDates;
        this.defaultLanguage = default_language;
        this.isIndex=isIndex;
    }

     private View view;

    @SuppressLint("SetTextI18n")
    public View Create_CheckInView() {
        view = LayoutInflater.from(context).inflate(layout.my_calendar_view, null, false);
        if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
            locale = new Locale(Constant.ARABIC_LANGUAGE_CODE);
        } else {
            locale = new Locale(Constant.ENGLISH_LANGUAGE_CODE);
        }
        calendarLayout=(LinearLayout)view.findViewById(id.calender_linearLayout);
      /*  if(isIndex)
            calendarLayout.setBackgroundColor(ContextCompat.getColor(context,color.white));
        else*/
            calendarLayout.setBackgroundColor(ContextCompat.getColor(context,color.white));

        calendar = Calendar.getInstance(locale);
        current_month = calendar.get(Calendar.MONTH) + 1;
        current_year = calendar.get(Calendar.YEAR);
        current_day = calendar.get(Calendar.DAY_OF_MONTH);
        month = selectedDatesArrayList.get(0).getMonth();
        year = selectedDatesArrayList.get(0).getYear();
        int   day = selectedDatesArrayList.get(0).getDay();
        calendar.set(year, month - 1, day);

        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] dayNames = symbols.getShortWeekdays();
        ArrayList<String> days = new ArrayList<>();
        for (String s : dayNames) {
            if (!s.trim().isEmpty()) {
                days.add(s);
            }
        }

        String[] months_Names = symbols.getMonths();
        for (String s : months_Names) {
            if (!s.trim().isEmpty()) {
                monthsNameArrayList.add(s);
            }
        }
        if (defaultLanguage.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
            //if arabic
            if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                Collections.reverse(days);
            }
        } else {
            //if arabic
            if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                Collections.reverse(days);
            }
        }

        displayDays(days);

        prevMonth = (ImageView) view.findViewById(id.prevMonth);
        prevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    nextMonth.setVisibility(View.VISIBLE);
                    getNextMonth();
                } else {
                    if(current_month==12)
                        if(month ==1 && current_year+1==year)
                            prevMonth.setVisibility(View.INVISIBLE);
                    if (current_month >=month-1 && current_year >= year)
                        prevMonth.setVisibility(View.INVISIBLE);
                    getPrevMonth();
                }
            }
        });

        currentMonth = (TextView) view.findViewById(id.currentMonth);

        //title for the current month
        currentMonth.setText(monthsNameArrayList.get(month-1) + " " + new SimpleDateFormat("yyyy", new Locale(Constant.ENGLISH_LANGUAGE_CODE)).format(calendar.getTime()));


        nextMonth = (ImageView) view.findViewById(id.nextMonth);
        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    if(current_month==12)
                        if(month ==1 && current_year+1==year)
                            nextMonth.setVisibility(View.INVISIBLE);
                    if (current_month >=month-1 && current_year >= year)
                        nextMonth.setVisibility(View.INVISIBLE);
                    getPrevMonth();
                } else {
                    prevMonth.setVisibility(View.VISIBLE);
                    getNextMonth();
                }
            }
        });

        if (current_year >= year) {
            {
                if (current_month >= month) {
                    if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                        prevMonth.setVisibility(View.INVISIBLE);
                    } else {
                        nextMonth.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }


        calendarView = (GridView) view.findViewById(id.calendarGridView);

        // Initialised
        adapter = new GridCellAdapter(context, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        return view;
    }

    private void getPrevMonth() {
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }

        setGridCellAdapterToDate(month, year);
    }


    private void getNextMonth() {
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }

        setGridCellAdapterToDate(month, year);
    }

    private void displayDays(ArrayList<String> days) {

        ArrayList<TextView> daysArrayList = new ArrayList<>();
        daysArrayList.add((TextView) view.findViewById(id.day1));
        daysArrayList.add((TextView) view.findViewById(id.day2));
        daysArrayList.add((TextView) view.findViewById(id.day3));
        daysArrayList.add((TextView) view.findViewById(id.day4));
        daysArrayList.add((TextView) view.findViewById(id.day5));
        daysArrayList.add((TextView) view.findViewById(id.day6));
        daysArrayList.add((TextView) view.findViewById(id.day7));

        for (int i = 0; i < daysArrayList.size(); i++) {
            daysArrayList.get(i).setText(days.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(context, month, year);
        calendar.set(year, month - 1, current_day);
        currentMonth.setText(monthsNameArrayList.get(month-1) + " " + new SimpleDateFormat("yyyy", new Locale(Constant.ENGLISH_LANGUAGE_CODE)).format(calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }


    private List<String> list = new ArrayList<>();
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};

    // Inner Class
    private class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private static final int DAY_OFFSET = 1;
        private int daysInMonth;
        private int currentDayOfMonth;
        private TextView calendarDatGridCellTextView;

        // Days in Current Month
        private GridCellAdapter(Context context,
                               int month, int year) {
            super();
            this._context = context;

            list = new ArrayList<>();
            setCurrentDayOfMonth(current_day);


            // Print Month
            printMonth(month, year);
        }

        private String getMonthAsString(int i) {
            return months[i];
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

       // prints month.
        private void printMonth(int months, int years) {
            int trailingSpaces ,daysInPrevMonth,prevMonth,prevYear,nextMonth,nextYear;
            int currentMonth = months - 1;
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);



            GregorianCalendar calendar = new GregorianCalendar(locale);
            calendar.set(years, currentMonth, 1);


            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = years;
                nextYear = years + 1;

            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = years - 1;
                nextYear = years;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;

            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = years;
                prevYear = years;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);

            }

            int currentWeekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;



            if (calendar.isLeapYear(calendar.get(Calendar.YEAR)))
                if (months == 2)
                    ++daysInMonth;
                else if (months == 3)
                    ++daysInPrevMonth;

            // Trailing Month days
            if (trailingSpaces == 0)//I added for constant height
            {
                for (int i = 0; i < 7; i++) {

                    list.add(String
                            .valueOf((daysInPrevMonth - 7 + DAY_OFFSET)
                                    + i)
                            + "-GREY"
                            + "-"
                            + getMonthAsString(prevMonth)
                            + "-"
                            + prevYear);
                }
            } else {
                for (int i = 0; i < trailingSpaces; i++) {

                    list.add(String
                            .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                                    + i)
                            + "-GREY"
                            + "-"
                            + getMonthAsString(prevMonth)
                            + "-"
                            + prevYear);
                }
            }


            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {

                if (i == getCurrentDayOfMonth() && current_month == months && current_year == years) {
                    list.add(String.valueOf(i) + "-BLUE" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                } else if (i < getCurrentDayOfMonth() && current_month == months && current_year == years) {
                    list.add(String.valueOf(i) + "-GREY" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                } else {
                    list.add(String.valueOf(i) + "-WHITE" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                }
            }


            // Leading Month days
            // list.size() %7 i have replace for constant height
            int limit1 = 42 - list.size();
            for (int i = 0; i < limit1; i++) {

                list.add(String.valueOf(i + 1) + "-GREY" + "-"
                        + getMonthAsString(nextMonth) + "-" + nextYear);
            }

            if (defaultLanguage.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                    ArrayList<String> arabicList = new ArrayList<>();
                    int limit = list.size() / 7;
                    for (int k = 0; k < limit; k++) {
                        int start = k * 7;
                        int end = k * 7 + 7;
                        List<String> subList = list.subList(start, end);
                        Collections.reverse(subList);
                        arabicList.addAll(subList);
                    }
                    list.clear();
                    list.addAll(arabicList);
                }
            } else {
                if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    ArrayList<String> arabicArrayList = new ArrayList<>();
                    int limit = list.size() / 7;
                    for (int k = 0; k < limit; k++) {
                        int start = k * 7;
                        int end = k * 7 + 7;
                        List<String> subList = list.subList(start, end);
                        Collections.reverse(subList);
                        arabicArrayList.addAll(subList);
                    }
                    list.clear();
                    list.addAll(arabicArrayList);
                }
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
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout.screen_gridcell, parent, false);
            }

            // Get a reference to the Day gridCell
            calendarDatGridCellTextView = (TextView) row.findViewById(id.calendarDatGridCellTextView);
            calendarDatGridCellTextView.setOnClickListener(this);

            // ACCOUNT FOR SPACING

            String[] dayColor = list.get(position).split("-");
            String theDay = dayColor[0];
            String theMonth = dayColor[2];
            String theYear = dayColor[3];

            // Set the Day GridCell
            calendarDatGridCellTextView.setText(theDay);
            calendarDatGridCellTextView.setTag(theDay + "-" + theMonth + "-" + theYear);


            if (dayColor[1].equals("GREY")) {
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.light_gray_text_color));
                calendarDatGridCellTextView.setTag(id.calendarDatGridCellTextView, false);
            }
            if (dayColor[1].equals("WHITE")) {
              /*  if(isIndex)
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.black));
                else*/
                    calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.black));
                calendarDatGridCellTextView.setTag(id.calendarDatGridCellTextView, true);
            }
            if (dayColor[1].equals("BLUE")) {
                calendarDatGridCellTextView.setTag(id.calendarDatGridCellTextView, true);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.orrange));
            }
            String back = getBackground(dayColor[0], dayColor[2], dayColor[3], position);
            if (back.equalsIgnoreCase("left")) {
                calendarDatGridCellTextView.setBackgroundResource(drawable.startdate_bg);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.white));
            } else if (back.equalsIgnoreCase("right")) {
                calendarDatGridCellTextView.setBackgroundResource(drawable.enddate_bg);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.white));

            } else if (back.equalsIgnoreCase("middle")) {
                calendarDatGridCellTextView.setBackgroundResource(drawable.middledate_bg);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.white));

            } else if (back.equalsIgnoreCase("single")) {
                calendarDatGridCellTextView.setBackgroundResource(drawable.singledate_bg);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.white));

            } else {
                calendarDatGridCellTextView.setBackgroundResource(0);
            }
            return row;
        }


        @Override
        public void onClick(View view) {
            if ((Boolean) view.getTag(id.calendarDatGridCellTextView)) {
                view.setBackgroundResource(drawable.singledate_bg);
                notifyDataSetChanged();
                if (dateSelectListener != null)
                    dateSelectListener.setCheckInDate((String) view.getTag());
            }
        }

        private int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }
    }

    private String getBackground(String day, String month, String year, int position) {
        String back = "no";
        for (int i = 0; i < selectedDatesArrayList.size(); i++) {
            String months = this.months[selectedDatesArrayList.get(i).getMonth() - 1];
            String days = Integer.toString(selectedDatesArrayList.get(i).getDay());
            String years = Integer.toString(selectedDatesArrayList.get(i).getYear());
            if (day.trim().equalsIgnoreCase(days.trim()) && month.trim().equalsIgnoreCase(months.trim()) && year.trim().equalsIgnoreCase(years.trim())) {
                if (defaultLanguage.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    if (language.equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                        if (position % 7 == 0 && (i == selectedDatesArrayList.size() - 1)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (position % 7 == 0) {
                            selectedDatesArrayList.get(i).setBackground("right");
                        } else if (i == 0 && ((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("left");
                        }
                        back = selectedDatesArrayList.get(i).getBackground();
                    } else {
                        if (position % 7 == 0 && (i == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (position % 7 == 0) {
                            selectedDatesArrayList.get(i).setBackground("right");
                        } else if (i == (selectedDatesArrayList.size() - 1) && ((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("left");
                        }
                        back = selectedDatesArrayList.get(i).getBackground();
                    }
                } else {
                    if (language.equalsIgnoreCase(Constant.ENGLISH_LANGUAGE_CODE)) {
                        if (position % 7 == 0 && (i == selectedDatesArrayList.size() - 1)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (position % 7 == 0) {
                            selectedDatesArrayList.get(i).setBackground("left");
                        } else if (i == 0 && ((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("right");
                        }
                        back = selectedDatesArrayList.get(i).getBackground();
                    } else {
                        if (position % 7 == 0 && (i == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (position % 7 == 0) {
                            selectedDatesArrayList.get(i).setBackground("left");
                        } else if (i == (selectedDatesArrayList.size() - 1) && ((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("single");
                        } else if (((position + 1) % 7 == 0)) {
                            selectedDatesArrayList.get(i).setBackground("right");
                        }
                        back = selectedDatesArrayList.get(i).getBackground();
                    }
                }

                break;
            }
        }
        return back;
    }
}
