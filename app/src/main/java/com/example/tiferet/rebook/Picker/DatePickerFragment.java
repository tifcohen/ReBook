package com.example.tiferet.rebook.Picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by TIFERET on 08-Jan-16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        listener.onSetDate(dayOfMonth, monthOfYear, year);
    }

    public interface OnDateSetListener{
        void onSetDate(int day, int month, int year);
    }

    OnDateSetListener listener;
    int day;
    int month;
    int year;

    public void setListener(OnDateSetListener listener){
        this.listener = listener;
    }

    public void setDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return dialog;
    }
}
