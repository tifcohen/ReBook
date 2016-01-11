package com.example.tiferet.rebook;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.example.tiferet.rebook.Fragments.MyProfileFragment;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.User;

import java.util.ArrayList;

public class ProfileActivity extends Activity implements MyProfileFragment.MyProfileFragmentDelegate{

    MyProfileFragment myProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myProfileFragment = (MyProfileFragment) getFragmentManager().findFragmentById(R.id.profileFragment);
        myProfileFragment.setDelegate(this);

        Log.d("TAG", "profile activity");
    }

    @Override
    public void OnAddNewBook() {

    }

    @Override
    public void OnBookProgress(String userId, Book book) {

    }

    @Override
    public void OnFollowingList(ArrayList<User> followers) {

    }

    @Override
    public void OnEditProfile(User user) {

    }

    @Override
    public void OnLogout() {

    }
}
