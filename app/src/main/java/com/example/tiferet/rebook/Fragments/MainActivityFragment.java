package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    EditText username_input;
    EditText password_input;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        username_input = (EditText) getActivity().findViewById(R.id.usr);
        password_input = (EditText) getActivity().findViewById(R.id.psw);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button loginBtn = (Button) view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = password_input.getText().toString();
                String username = username_input.getText().toString().toLowerCase().trim().replaceAll(" +", " ");

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {
                            Toast.makeText(getActivity().getApplicationContext(), "Welcome to ReBook!", Toast.LENGTH_LONG).show();
                            if (delegate != null) {
                                delegate.OnNewsFeed();
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "User does not exist.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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


    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        activity.menuIdToDisplay = R.menu.menu_main;
        activity.invalidateOptionsMenu();
    }

}
