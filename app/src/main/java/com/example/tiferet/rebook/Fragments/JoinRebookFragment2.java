package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment2 extends Fragment {
    public interface JoinRebookFragment2Delegate{

    }

    User user;
    JoinRebookFragment2Delegate delegate;

    public void setDelegate(JoinRebookFragment2Delegate delegate) {
        this.delegate = delegate;
    }

    public JoinRebookFragment2(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_rebook_fragment_2, container, false);

        return view;
    }

    public void setUser(User user) {this.user = user;}
}
