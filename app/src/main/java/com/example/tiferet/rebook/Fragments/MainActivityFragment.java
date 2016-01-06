package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tiferet.rebook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public interface MainActivityFragmentDelegate{
        void OnNewsFeed();
        void OnJoinRebook();
    }

    MainActivityFragmentDelegate delegate;
    public void setDelegate(MainActivityFragmentDelegate delegate){
        this.delegate = delegate;
    }


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button loginBtn = (Button) view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate!=null){
                    delegate.OnNewsFeed();
                }
            }
        });
        return view;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.generalBtn : {
                if (this.delegate != null)
                    delegate.OnJoinRebook();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
