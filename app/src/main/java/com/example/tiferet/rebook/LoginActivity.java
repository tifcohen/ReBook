package com.example.tiferet.rebook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        setTitle(R.string.action_sign_in_short);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "My Profile Pressed");
        switch (item.getItemId()) {
            case R.id.join_rebook:{
                onJoin();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join, menu);
        return true;
    }

    private void onJoin() {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    public void onNewsFeed(){
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }
}

