package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 07-Jan-16.
 */
public class EditProfileFragment extends Fragment{
    public interface EditProfileFragmentDelegate{

    }

    EditProfileFragmentDelegate delegate;

    public void setDelegate(EditProfileFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public EditProfileFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        return view;
    }
}
