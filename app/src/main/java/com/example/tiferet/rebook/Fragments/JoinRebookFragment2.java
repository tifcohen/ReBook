package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.Picker.DateEditText;
import com.example.tiferet.rebook.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment2 extends Fragment {
    public interface JoinRebookFragment2Delegate{
        void onNewsFeed();
        void onCancel();
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
        final EditText email = (EditText) view.findViewById(R.id.Join2EmailEditText);
        final EditText fName = (EditText) view.findViewById(R.id.addUserFName);
        final EditText lName = (EditText) view.findViewById(R.id.addUserLName);
        final DateEditText birthday = (DateEditText) view.findViewById(R.id.addUserBirthDate);

        Button save = (Button) view.findViewById(R.id.saveNewUserBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fName.getText().toString().equals("") || lName.getText().toString().equals(""))
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Please fill both name fields", Toast.LENGTH_LONG)
                            .show();
                }
                else
                {
                    //creating local user
                    user.setfName(fName.getText().toString());
                    user.setlName(lName.getText().toString());
                    user.setBirthDate(birthday.getText().toString());
                    user.setEmail(email.getText().toString());

                    //creating Parse user
                    ParseUser parseUser = new ParseUser();

                    parseUser.setUsername(user.getUsername());
                    parseUser.setPassword(user.getUserId());
                    if (email.getText().toString().equals(""))
                        parseUser.setEmail(user.getUsername() + "@rebook.co.il");
                    else
                        parseUser.setEmail(user.getEmail());
                    parseUser.put("fName",user.getfName());
                    parseUser.put("lName", user.getlName());
                    parseUser.put("birthday", user.getBirthDate());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                delegate.onNewsFeed();
                            } else {
                                Toast.makeText(
                                        getActivity().getApplicationContext(), "Sign up failed, Please try again.", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.onCancel();
            }
        });
        return view;
    }

    public void setUser(User user) {this.user = user;}
}
