package com.example.genovagreen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Spedizioni4 extends AppCompatActivity  {

    Button dateButton, timeButton;
    TextView dateText, timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spedizioni4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dateButton = findViewById(R.id.button2);
        timeButton = findViewById(R.id.button3);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });


    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        final int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, dayOfMonth);

                CharSequence charSequence = DateFormat.format("FFFF, dd MMM yyyy", calendar1);
                dateText.setText(charSequence);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }


    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeString = "hour " + hourOfDay + "minute " + minute;
                timeText.setText(timeString);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hourOfDay);
                calendar1.set(Calendar.MINUTE, minute);

                CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
                timeText.setText(charSequence);

            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();
    }

}