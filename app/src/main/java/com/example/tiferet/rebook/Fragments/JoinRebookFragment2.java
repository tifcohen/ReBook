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
import com.example.tiferet.rebook.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment2 extends Fragment {
    public interface JoinRebookFragment2Delegate{
        // void OnMyProfileFirst(User user);
        void OnNewsFeed();
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
        final EditText birthday = (EditText) view.findViewById(R.id.addUserBirthDate);

        Button save = (Button) view.findViewById(R.id.saveNewUserBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fName.getText().toString().equals("") || lName.getText().toString().equals(""))
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Please fill the name fields.", Toast.LENGTH_LONG)
                            .show();
                }
                else
                {
                    String parseUsername = user.getUsername();
                    String parsePassword = user.getUserId();
                    String parseEmail = email.getText().toString();
                    String parseFirstName = fName.getText().toString();
                    String parseLastName = lName.getText().toString();
                    String parseBirthday = birthday.getText().toString();

                    ParseUser parseUser = new ParseUser();

                    parseUser.setUsername(parseUsername);
                    parseUser.setPassword(parsePassword);
                    if (parseEmail.equals(""))
                        parseUser.setEmail(parseUsername + "@rebook");
                    else
                        parseUser.setEmail(parseEmail);
                    parseUser.put("fName",parseFirstName);
                    parseUser.put("lName", parseLastName);
                    parseUser.put("birthday", parseBirthday);

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                UserDB.getInstance().addUser(user);
                                Log.d("TAG", "User was added");
                                delegate.OnNewsFeed();

                            } else {
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "Sign up failed, Please try again.", Toast.LENGTH_LONG)
                                        .show();

                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });





        return view;
    }

    public void setUser(User user) {this.user = user;}
}
