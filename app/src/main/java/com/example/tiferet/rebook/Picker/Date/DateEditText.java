package com.example.tiferet.rebook.Picker.Date;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by TIFERET on 07-Jan-16.
 */
public class DateEditText extends EditText {
    public DateEditText(Context context) {
        super(context);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int day;
    int month;
    int year;

    private void init() {
        setInputType(0);
        Calendar cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            DatePicker dpf = new DatePicker();
            dpf.setOnDateSetListener(this);
            dpf.setDate(year,month,day);
            dpf.show(((Activity)getContext()).getFragmentManager(),"TAG");
        }
        return true;
    }

    //@Override
    public void OnDateSet(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;

        setText("" + this.day + "/" + (this.month+1)+ "/" + this.year);
    }
}
