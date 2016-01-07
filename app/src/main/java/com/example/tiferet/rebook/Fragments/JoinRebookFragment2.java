package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment2 extends Fragment {
    public interface JoinRebookFragment2Delegate{
        // void OnMyProfileFirst(User user);
       void OnNewsFeed(User user);
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

        final EditText fName = (EditText) view.findViewById(R.id.addUserFName);
        final EditText lName = (EditText) view.findViewById(R.id.addUserLName);
        final EditText birthdate = (EditText) view.findViewById(R.id.addUserBirthDate);
        final EditText profPic = (EditText) view.findViewById(R.id.addProfilePic);

        Button save = (Button) view.findViewById(R.id.saveNewUserBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setfName(fName.getText().toString());
                user.setlName(lName.getText().toString());
                user.setBirthDate(birthdate.getText().toString());
                user.setProfPicture(profPic.getText().toString());
                UserDB.getInstance().addUser(user);
                Log.d("TAG", "User was added");
                delegate.OnNewsFeed(user);
            }
        });





        return view;
    }

    public void setUser(User user) {this.user = user;}
}
