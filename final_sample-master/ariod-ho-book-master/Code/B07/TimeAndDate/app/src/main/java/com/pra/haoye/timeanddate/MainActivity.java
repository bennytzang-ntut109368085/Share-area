package com.pra.haoye.timeanddate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends Activity {
    protected Button clsbtnobj;
    protected EditText dateetobj, timeetobj;
    protected Calendar c = Calendar.getInstance();
    protected TextView showtvobj;
    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private View.OnClickListener returnBtn = new View.OnClickListener(){
        public void onClick(View v){
            MainActivity.this.finish();
        }
    };

    protected void findViews() {

        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(returnBtn);

        dateetobj = findViewById(R.id.dateet);
        timeetobj = findViewById(R.id.timeet);
        showtvobj = findViewById(R.id.showtv);

        dateetobj.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(
                                MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                if(month == 8 && day == 28){
                                    String format = setDateFormat( year, month, day);
                                    dateetobj.setText(format);
                                    showtvobj.setText("Wishing you a happy Teacher’s Day !");
                                } else {
                                    String format = setDateFormat( year, month, day);
                                    dateetobj.setText(format);
                                    showtvobj.setText("");
                                }
                            }
                        }, mYear, mMonth, mDay).show();
                    }
                }
        );

        timeetobj.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);
                        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener(){
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeetobj.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, true).show();

                    }
                }
        );

    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "/"
                + String.valueOf(monthOfYear + 1) + "/"
                + String.valueOf(dayOfMonth);
    }
}
