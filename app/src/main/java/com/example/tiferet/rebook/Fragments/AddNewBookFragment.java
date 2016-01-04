package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 04-Jan-16.
 */
public class AddNewBookFragment extends Fragment {
    public interface AddNewBookFragmentDelegate{

    }

    AddNewBookFragmentDelegate delegate;
    public void setDelegate(AddNewBookFragmentDelegate delegate){ this.delegate = delegate;}

    public AddNewBookFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);


        return view;
    }

}
