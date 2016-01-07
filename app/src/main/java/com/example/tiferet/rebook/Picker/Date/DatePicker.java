package com.example.tiferet.rebook.Picker.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by TIFERET on 07-Jan-16.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    interface onDateSetListener{
        public void OnDateSet(int day, int month, int year);
    }

    private onDateSetListener listener;
    public void setOnDateSetListener(onDateSetListener ls){
        listener = ls;
    }

    int day;
    int month;
    int year;

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        listener.OnDateSet(year,monthOfYear, dayOfMonth);
    }

    public void setOnDateSetListener(DateEditText dateEditText) {
    }

    public void setDate(int year, int month, int day){
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new DatePickerDialog(getActivity(),this,year,month,day);
        return dialog;
    }
}
