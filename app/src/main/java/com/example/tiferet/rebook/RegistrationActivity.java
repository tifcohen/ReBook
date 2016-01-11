package com.example.tiferet.rebook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.example.tiferet.rebook.Fragments.JoinRebookFragment;
import com.example.tiferet.rebook.Fragments.JoinRebookFragment2;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;

public class RegistrationActivity extends Activity implements JoinRebookFragment.JoinRebookFragmentDelegate,
        JoinRebookFragment2.JoinRebookFragment2Delegate{

    JoinRebookFragment joinRebookFragment;
    JoinRebookFragment2 joinRebookFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        joinRebookFragment = (JoinRebookFragment) getFragmentManager().findFragmentById(R.id.registration);
        joinRebookFragment.setDelegate(this);
    }

    @Override
    public void onJoinRebook2(User user) {
        Log.d("TAG", "User" + user.getEmail());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        joinRebookFragment2 = new JoinRebookFragment2();
        joinRebookFragment2.setUser(user);
        joinRebookFragment2.setDelegate(this);
        ft.add(R.id.registration_container, joinRebookFragment2);
        ft.hide(joinRebookFragment);
        ft.addToBackStack("join rebook");
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onNewsFeed() {
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancel() {
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
    }
}
