package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment extends Fragment {
    public interface JoinRebookFragmentDelegate{

    }

    JoinRebookFragmentDelegate delegate;
    public void setDelegate(JoinRebookFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public JoinRebookFragment(){

    }
}
