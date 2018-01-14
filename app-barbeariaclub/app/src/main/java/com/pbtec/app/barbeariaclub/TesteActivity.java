package com.pbtec.app.barbeariaclub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TesteActivity extends Activity {

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        calendarView = (CalendarView)findViewById(R.id.calendarView2);
        calendarView.setMinDate(new GregorianCalendar().getTimeInMillis()-1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(), "teste"+year, Toast.LENGTH_LONG).show();
            }
        });

    }

}
