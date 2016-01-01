package com.example.tiferet.rebook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tiferet.rebook.Fragments.MainActivityFragment;
import com.example.tiferet.rebook.Fragments.NewsFeedFragment;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MainActivityFragmentDelegate,
        NewsFeedFragment.NewsFeedFragmentDelegate {

    String thisFrag = "login";
    MainActivityFragment loginFragment;
    NewsFeedFragment newsFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginFragment = (MainActivityFragment) getFragmentManager().findFragmentById(R.id.loginFragment);
        newsFeedFragment = (NewsFeedFragment) getFragmentManager().findFragmentById(R.id.newsFeedFragment);
        //editStudent = (EditStudentFragment) getFragmentManager().findFragmentById(R.id.editStudent);
        //addStudent = (AddStudentFragment) getFragmentManager().findFragmentById(R.id.addStudent);

        loginFragment.setDelegate(this);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.hide(newsFeedFragment);
        //ft.hide(editStudent);
        //ft.hide(addStudent);
        ft.commit();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnNewsFeed() {
        if (thisFrag.equals("login")) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            newsFeedFragment = new NewsFeedFragment();
            newsFeedFragment.setDelegate(this);
            ft.add(R.id.container, newsFeedFragment);
            ft.hide(loginFragment);
            ft.addToBackStack("login");
            ft.show(newsFeedFragment);
            ft.commit();
            thisFrag = "newsfeed";
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            String name = getFragmentManager().getBackStackEntryAt(count - 1).getName();
            switch (name) {
                case "newsfeed":
                    thisFrag = "login";
                    break;
                default:
                    thisFrag = "login";
                    break;
            }
            invalidateOptionsMenu();
            this.getFragmentManager().popBackStack();
        } else {
            finish();
        }

    }
}
