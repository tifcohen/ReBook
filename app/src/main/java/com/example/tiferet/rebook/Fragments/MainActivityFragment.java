package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public interface MainActivityFragmentDelegate{
        void OnNewsFeed(User user);
        void OnJoinRebook();
    }

    MainActivityFragmentDelegate delegate;
    //User user;

    public void setDelegate(MainActivityFragmentDelegate delegate){
        this.delegate = delegate;
    }


    public MainActivityFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        final EditText email = (EditText) view.findViewById(R.id.usr);
        EditText psw = (EditText) view.findViewById(R.id.psw);

        Button loginBtn = (Button) view.findViewById(R.id.loginBtn);

        //final User temp = new User("123",email.getText().toString(),"name", "name", "prof", "birtheday");
        //user.setEmail(email.getText().toString());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User temp = new User("123",email.getText().toString(),"name", "name", "prof", "birtheday");
                delegate.OnNewsFeed(temp);
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
