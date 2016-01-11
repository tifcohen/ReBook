package com.example.tiferet.rebook;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.tiferet.rebook.Fragments.AddNewBookFragment;
import com.example.tiferet.rebook.Fragments.BookProgressFragment;
import com.example.tiferet.rebook.Fragments.EditProfileFragment;
import com.example.tiferet.rebook.Fragments.FollowingListFragment;
import com.example.tiferet.rebook.Fragments.JoinRebookFragment;
import com.example.tiferet.rebook.Fragments.JoinRebookFragment2;
import com.example.tiferet.rebook.Fragments.MainActivityFragment;
import com.example.tiferet.rebook.Fragments.MyProfileFragment;
import com.example.tiferet.rebook.Fragments.NewsFeedFragment;
import com.example.tiferet.rebook.Fragments.OtherProfileFragment;
import com.example.tiferet.rebook.Fragments.OthersReviewFragment;
import com.example.tiferet.rebook.Fragments.SinglePostFragment;
import com.example.tiferet.rebook.Fragments.UpdateBookProgressFragment;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.parse.ParseUser;

import java.util.ArrayList;

public class MainActivity extends Activity implements MainActivityFragment.MainActivityFragmentDelegate,
        NewsFeedFragment.NewsFeedFragmentDelegate, SinglePostFragment.SinglePostFragmentDelegate,
        MyProfileFragment.MyProfileFragmentDelegate, AddNewBookFragment.AddNewBookFragmentDelegate,
        BookProgressFragment.BookProgressFragmentDelegate, UpdateBookProgressFragment.UpdateBookProgressFragmentDelegate,
        OthersReviewFragment.OthersReviewFragmentDelegate, FollowingListFragment.FollowingListFragmentDelegate,
        JoinRebookFragment.JoinRebookFragmentDelegate, JoinRebookFragment2.JoinRebookFragment2Delegate,
        EditProfileFragment.EditProfileFragmentDelegate{

    String thisFrag = "login";
    MainActivityFragment loginFragment;
    NewsFeedFragment newsFeedFragment;
    SinglePostFragment singlePostFragment;
    MyProfileFragment myProfileFragment;
    AddNewBookFragment addNewBookFragment;
    BookProgressFragment bookProgressFragment;
    UpdateBookProgressFragment updateBookProgressFragment;
    OthersReviewFragment othersReviewFragment;
    FollowingListFragment followingListFragment;
    OtherProfileFragment otherProfileFragment;
    JoinRebookFragment joinRebookFragment;
    JoinRebookFragment2 joinRebookFragment2;
    EditProfileFragment editProfileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        loginFragment = (MainActivityFragment) getFragmentManager().findFragmentById(R.id.loginFragment);
        loginFragment.setDelegate(this);
    }

    public int menuIdToDisplay = R.menu.menu_main;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuIdToDisplay, menu);
        return true;
    }

    @Override
    public void OnNewsFeed(User user) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int temp = fm.getBackStackEntryCount()-1;
        thisFrag = ""+ temp;
        Fragment currentFragment = getFragmentManager().findFragmentByTag(thisFrag);
        newsFeedFragment = new NewsFeedFragment();
        newsFeedFragment.setUser(user);
        newsFeedFragment.setDelegate(this);
        thisFrag = "" + fm.getBackStackEntryCount();
        ft.add(R.id.container, newsFeedFragment, thisFrag);
        if(temp<1){
            ft.hide(loginFragment);
        }
        else
            ft.hide(currentFragment);
        //ft.hide(joinRebookFragment2);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnSinglePost(Post post) {
        Log.d("TAG", "post selected " + post.getPostID());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        singlePostFragment = new SinglePostFragment();
        singlePostFragment.setPost(post);
        singlePostFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, singlePostFragment, thisFrag);
        ft.hide(newsFeedFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    public void OnMyProfile(User user) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setUser(user);
        myProfileFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, myProfileFragment, thisFrag);
        ft.hide(newsFeedFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
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
        Log.d("TAG", "Book selected " + book.getBookID());
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
    public void OnJoinRebook() {
        Log.d("TAG", "User");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        joinRebookFragment = new JoinRebookFragment();
        joinRebookFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, joinRebookFragment, thisFrag);
        ft.hide(loginFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnJoinRebook2(User user) {
        Log.d("TAG", "User" + user.getEmail());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        joinRebookFragment2 = new JoinRebookFragment2();
        joinRebookFragment2.setUser(user);
        joinRebookFragment2.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, joinRebookFragment2, thisFrag);
        ft.hide(joinRebookFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void onCancel() {
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
}

    @Override
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

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        int temp = fm.getBackStackEntryCount() - 2;
        if (temp > 0) {
            thisFrag = "" + temp;
            Fragment currentFragment = getFragmentManager().findFragmentByTag(thisFrag);
            FragmentTransaction ft = fm.beginTransaction();
            ft.detach(currentFragment);
            ft.attach(currentFragment);
            ft.commit();
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void onSaveChanges() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setDelegate(this);
        thisFrag = ""+ fm.getBackStackEntryCount();
        ft.add(R.id.container, myProfileFragment, thisFrag);
        ft.hide(editProfileFragment);
        ft.addToBackStack(thisFrag);
        ft.commit();
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
        ft.hide(newsFeedFragment);
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
                ft.hide(newsFeedFragment);
                ft.addToBackStack(thisFrag);
                ft.commit();
                invalidateOptionsMenu();
            }
        });
    }
}
