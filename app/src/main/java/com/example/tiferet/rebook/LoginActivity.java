package com.example.tiferet.rebook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiferet.rebook.Model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

    EditText username_input;
    EditText password_input;
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(currentUser!=null){
            onNewsFeed();
        }

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_input = (EditText) findViewById(R.id.usr);
                password_input = (EditText) findViewById(R.id.psw);
                String password = password_input.getText().toString();
                String username = username_input.getText().toString().toLowerCase().trim().replaceAll(" +", " ");

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Toast.makeText(getApplicationContext(), "Welcome to Rebook!", Toast.LENGTH_LONG).show();
                            onNewsFeed();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Unable to sign you in, Username and/or Password are incorrect", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });

            }

        });
    }

    public void onNewsFeed(){
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }
}

