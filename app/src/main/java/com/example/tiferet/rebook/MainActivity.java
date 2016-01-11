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

import com.example.tiferet.rebook.Fragments.AddNewBookFragment;
import com.example.tiferet.rebook.Fragments.BookProgressFragment;
import com.example.tiferet.rebook.Fragments.EditProfileFragment;
import com.example.tiferet.rebook.Fragments.FollowingListFragment;
import com.example.tiferet.rebook.Fragments.JoinRebookFragment;
import com.example.tiferet.rebook.Fragments.JoinRebookFragment2;
import com.example.tiferet.rebook.Fragments.MyProfileFragment;
import com.example.tiferet.rebook.Fragments.OtherProfileFragment;
import com.example.tiferet.rebook.Fragments.OthersReviewFragment;
import com.example.tiferet.rebook.Fragments.UpdateBookProgressFragment;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.parse.ParseUser;

import java.util.ArrayList;

public class MainActivity extends Activity implements
        MyProfileFragment.MyProfileFragmentDelegate, AddNewBookFragment.AddNewBookFragmentDelegate,
        BookProgressFragment.BookProgressFragmentDelegate, UpdateBookProgressFragment.UpdateBookProgressFragmentDelegate,
        OthersReviewFragment.OthersReviewFragmentDelegate, FollowingListFragment.FollowingListFragmentDelegate,
        EditProfileFragment.EditProfileFragmentDelegate{

    String thisFrag = "login";
    MyProfileFragment myProfileFragment;
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

        ParseUser pu = ParseUser.getCurrentUser();
        User user = new User(pu);

        myProfileFragment = (MyProfileFragment) getFragmentManager().findFragmentById(R.id.profileFragment);
        myProfileFragment.setUser(user);
        myProfileFragment.setDelegate(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "My Profile Pressed");
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
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onNewsFeed() {
        Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
        startActivity(intent);

    }

    public void onLogout() {
        ParseUser.logOut();
        Log.d("TAG", "on log out");
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
        Log.d("TAG", "on add new book");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addNewBookFragment = new AddNewBookFragment();
        addNewBookFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, addNewBookFragment, thisFrag);
        ft.hide(myProfileFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnBookProgress(String userId, Book book) {

        bookProgressFragment = new BookProgressFragment();
        bookProgressFragment.setBook(book);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!userId.equals(""))
        {
            User user = new User(ParseUser.getCurrentUser());
            bookProgressFragment.setCurr(user);
            bookProgressFragment.setUserId(userId);
        }
        else
        {
            bookProgressFragment.setCurr(null);
            bookProgressFragment.setUserId(null);
        }

        bookProgressFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, bookProgressFragment,thisFrag);
        ft.hide(myProfileFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }



    @Override
    public void OnFollowingList(ArrayList<User> followers) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        followingListFragment = new FollowingListFragment();
        followingListFragment.setDelegate(this);
        followingListFragment.setData(followers);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, followingListFragment, thisFrag);
        ft.hide(myProfileFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnEditProfile(User user) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        editProfileFragment = new EditProfileFragment();
        editProfileFragment.setDelegate(this);
        editProfileFragment.setUser(user);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, editProfileFragment, thisFrag);
        ft.hide(myProfileFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnUpdateProgress(Book book) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        updateBookProgressFragment = new UpdateBookProgressFragment();
        updateBookProgressFragment.setBook(book);
        updateBookProgressFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, updateBookProgressFragment, thisFrag);
        ft.hide(bookProgressFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnOthersReview(Book book) {
        Log.d("TAG", "Book selected " + book.getBookID());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        othersReviewFragment = new OthersReviewFragment();
        othersReviewFragment.setBook(book);
        othersReviewFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, othersReviewFragment, thisFrag);
        ft.hide(bookProgressFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onCancel() {
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
}

    @Override //need to fix
    public void onSave() {
        FragmentManager fm = getFragmentManager();
        int temp = fm.getBackStackEntryCount() - 2;
        thisFrag = ""+ temp;
        Fragment currentFragment = getFragmentManager().findFragmentByTag(thisFrag);
        FragmentTransaction ft = fm.beginTransaction();
        ft.detach(currentFragment);
        ft.attach(currentFragment);
        ft.commit();
        getFragmentManager().popBackStack();
    }

    public void onClickUsername(View v){
        User user = (User) v.getTag();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setUser(user);
        myProfileFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, myProfileFragment, thisFrag);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }


    public void onClickBookname(View v){
        Post post = (Post) v.getTag();
        //final String userId = post.getUserID();
        final String bookId = post.getBookID();

        Model.getInstance().getBookByIdAsync(post.getBookID(), new Model.GetBookListener() {
            @Override
            public void onBookArrived(Book book) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                bookProgressFragment = new BookProgressFragment();
                bookProgressFragment.setBook(book);
                bookProgressFragment.setCurr(null);
                //bookProgressFragment.setDelegate(this);

                //othersReviewFragment.setDelegate(this)
                thisFrag = "" + fm.getBackStackEntryCount();
                ft.add(R.id.container, bookProgressFragment, thisFrag);
                ft.addToBackStack(thisFrag);
                ft.commit();
                invalidateOptionsMenu();
            }
        });
    }
}
