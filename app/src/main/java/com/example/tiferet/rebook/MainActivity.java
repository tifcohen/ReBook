package com.example.tiferet.rebook;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.tiferet.rebook.Fragments.AddNewBookFragment;
import com.example.tiferet.rebook.Fragments.BookProgressFragment;
import com.example.tiferet.rebook.Fragments.EditProfileFragment;
import com.example.tiferet.rebook.Fragments.FollowingListFragment;
import com.example.tiferet.rebook.Fragments.MyProfileFragment;
import com.example.tiferet.rebook.Fragments.OthersReviewFragment;
import com.example.tiferet.rebook.Fragments.UpdateBookProgressFragment;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends Activity implements
        MyProfileFragment.MyProfileFragmentDelegate, AddNewBookFragment.AddNewBookFragmentDelegate,
        BookProgressFragment.BookProgressFragmentDelegate, UpdateBookProgressFragment.UpdateBookProgressFragmentDelegate,
        OthersReviewFragment.OthersReviewFragmentDelegate, FollowingListFragment.FollowingListFragmentDelegate,
        EditProfileFragment.EditProfileFragmentDelegate{

    String thisFrag = "0";
    Fragment currFragment = new Fragment();
    Fragment prevFragment = new Fragment();

    Stack<Fragment> stack;

    MyProfileFragment myProfileFragment;
    MyProfileFragment otherProfile;
    AddNewBookFragment addNewBookFragment;
    BookProgressFragment bookProgressFragment;
    UpdateBookProgressFragment updateBookProgressFragment;
    OthersReviewFragment othersReviewFragment;
    FollowingListFragment followingListFragment;
    EditProfileFragment editProfileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        stack = new Stack<>();

        String action = getIntent().getExtras().getString("fragment");

        myProfileFragment = (MyProfileFragment) getFragmentManager().findFragmentById(R.id.profileFragment);
        myProfileFragment.setDelegate(this);
        stack.push(myProfileFragment);

        if(action.equals("user")) {
            getFragmentManager().popBackStack();
            String userId = getIntent().getExtras().getString("userId");
            User user = new User(userId);
            onClickUsername(user);
        }
        if(action.equals("book")){
            getFragmentManager().popBackStack();
            String bookId = getIntent().getExtras().getString("bookId");
            onClickBookname(bookId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:{
                onLogout();
                return true;
            }
            case R.id.action_add_book: {
                OnAddNewBook();
                return true;
            }
            case R.id.action_news_feed: {
                onNewsFeed();
                return true;
            }
            case R.id.action_my_profile: {
                onMyProfile();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onMyProfile() {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setDelegate(this);
        ft.add(R.id.container, myProfileFragment, myProfileFragment.toString());
        //stack.push(currFragment);
        stack.push(myProfileFragment);
        ft.hide(currFragment);
        ft.addToBackStack(myProfileFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    public void onNewsFeed() {
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(intent);
    }

    public void onLogout() {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public void OnAddNewBook() {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addNewBookFragment = new AddNewBookFragment();
        addNewBookFragment.setDelegate(this);
        ft.add(R.id.container, addNewBookFragment, addNewBookFragment.toString());
        //stack.push(currFragment);
        stack.push(addNewBookFragment);
        ft.hide(currFragment);
        ft.addToBackStack(addNewBookFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnBookProgress(String userId, Book book) {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        bookProgressFragment = new BookProgressFragment();
        bookProgressFragment.setBook(book);
        if (!userId.equals("")) {
            User user = new User(ParseUser.getCurrentUser());
            bookProgressFragment.setCurr(user);
            bookProgressFragment.setUserId(userId);
        }
        else {
            bookProgressFragment.setCurr(null);
            bookProgressFragment.setUserId(null);
        }
        bookProgressFragment.setDelegate(this);
        ft.add(R.id.container, bookProgressFragment, bookProgressFragment.toString());
        //stack.push(currFragment);
        stack.push(bookProgressFragment);
        ft.hide(currFragment);
        ft.addToBackStack(bookProgressFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnFollowingList(ArrayList<User> followers) {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        followingListFragment = new FollowingListFragment();
        followingListFragment.setDelegate(this);
        followingListFragment.setData(followers);
        ft.add(R.id.container, followingListFragment, followingListFragment.toString());
        //stack.push(currFragment);
        stack.push(followingListFragment);
        ft.hide(currFragment);
        ft.addToBackStack(followingListFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnEditProfile(User user) {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        editProfileFragment = new EditProfileFragment();
        editProfileFragment.setDelegate(this);
        editProfileFragment.setUser(user);
        ft.add(R.id.container, editProfileFragment, editProfileFragment.toString());
        //stack.push(currFragment);
        stack.push(editProfileFragment);
        ft.hide(currFragment);
        ft.addToBackStack(editProfileFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnUpdateProgress(Book book) {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        updateBookProgressFragment = new UpdateBookProgressFragment();
        updateBookProgressFragment.setBook(book);
        updateBookProgressFragment.setDelegate(this);
        ft.add(R.id.container, updateBookProgressFragment, updateBookProgressFragment.toString());
        //stack.push(currFragment);
        stack.push(updateBookProgressFragment);
        ft.hide(currFragment);
        ft.addToBackStack(updateBookProgressFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnOthersReview(Book book) {
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        othersReviewFragment = new OthersReviewFragment();
        othersReviewFragment.setBook(book);
        othersReviewFragment.setDelegate(this);
        ft.add(R.id.container, othersReviewFragment, othersReviewFragment.toString());
        //stack.push(currFragment);
        stack.push(othersReviewFragment);
        ft.hide(currFragment);
        ft.addToBackStack(othersReviewFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onCancel() {
        stack.pop();
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
}

    @Override
    public void onSave() {
        currFragment = stack.pop();
        prevFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //getFragmentManager().popBackStack();
        //ft.add(R.id.container, prevFragment);
        ft.show(prevFragment);
        stack.push(prevFragment);
        //stack.push(myProfileFragment);
        ft.hide(currFragment);
        //ft.addToBackStack(prevFragment.toString());
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        stack.pop();
    }

    public void onClickUsername(User user){
        currFragment = stack.peek();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        otherProfile = new MyProfileFragment();
        otherProfile.setDelegate(this);
        otherProfile.setUser(user);
        ft.add(R.id.container, otherProfile, otherProfile.toString());
        //stack.push(currFragment);
        stack.push(otherProfile);
        ft.hide(currFragment);
        ft.addToBackStack(otherProfile.toString());
        ft.commit();
        invalidateOptionsMenu();
    }


    public void onClickBookname(String bookId){
        Model.getInstance().getBookByIdAsync(bookId, new Model.GetBookListener() {
            @Override
            public void onBookArrived(Book book) {
                currFragment = stack.peek();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                bookProgressFragment = new BookProgressFragment();
                bookProgressFragment.setBook(book);
                bookProgressFragment.setCurr(null);
                ft.add(R.id.container, bookProgressFragment, bookProgressFragment.toString());
                stack.push(bookProgressFragment);
                ft.hide(currFragment);
                ft.addToBackStack(bookProgressFragment.toString());
                ft.commit();
                invalidateOptionsMenu();
            }
        });
    }
}
