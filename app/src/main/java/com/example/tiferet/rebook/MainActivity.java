package com.example.tiferet.rebook;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tiferet.rebook.Fragments.AddNewBookFragment;
import com.example.tiferet.rebook.Fragments.BookProgressFragment;
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
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.parse.Parse;

public class MainActivity extends Activity implements MainActivityFragment.MainActivityFragmentDelegate,
        NewsFeedFragment.NewsFeedFragmentDelegate, SinglePostFragment.SinglePostFragmentDelegate,
        MyProfileFragment.MyProfileFragmentDelegate, AddNewBookFragment.AddNewBookFragmentDelegate,
        BookProgressFragment.BookProgressFragmentDelegate, UpdateBookProgressFragment.UpdateBookProgressFragmentDelegate,
        OthersReviewFragment.OthersReviewFragmentDelegate, FollowingListFragment.FollowingListFragmentDelegate,
        JoinRebookFragment.JoinRebookFragmentDelegate, JoinRebookFragment2.JoinRebookFragment2Delegate{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
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
    public void OnNewsFeed() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        newsFeedFragment = new NewsFeedFragment();
        newsFeedFragment.setDelegate(this);
        ft.add(R.id.container, newsFeedFragment);
        ft.hide(loginFragment);
        ft.hide(joinRebookFragment2);
        ft.addToBackStack(loginFragment.toString());
        //ft.show(newsFeedFragment);
        ft.commit();
        //thisFrag = "newsfeed";
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
        ft.add(R.id.container, singlePostFragment);
        ft.hide(newsFeedFragment);
        ft.addToBackStack(newsFeedFragment.toString());
        //ft.show(singlePostFragment);
        //thisFrag = "singlePost";
        ft.commit();
        invalidateOptionsMenu();
    }

    public void OnMyProfile() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setDelegate(this);
        ft.add(R.id.container, myProfileFragment);
        ft.hide(newsFeedFragment);
        ft.addToBackStack(newsFeedFragment.toString());
        //ft.show(myProfileFragment);
        //thisFrag = "myProfile";
        ft.commit();
        invalidateOptionsMenu();
        Log.d("TAG", "on my profile");
    }

    @Override
    public void OnAddNewBook() {
        Log.d("TAG", "on add new book");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        addNewBookFragment = new AddNewBookFragment();
        addNewBookFragment.setDelegate(this);
        ft.add(R.id.container, addNewBookFragment);
        ft.hide(myProfileFragment);
        ft.addToBackStack(myProfileFragment.toString());
        //ft.show(addNewBookFragment);
        //thisFrag = "addNewBook";
        ft.commit();
        invalidateOptionsMenu();
    }

    @Override
    public void OnBookProgress(Book book) {
        Log.d("TAG", "Book selected " + book.getBookID());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        bookProgressFragment = new BookProgressFragment();
        bookProgressFragment.setBook(book);
        bookProgressFragment.setDelegate(this);
        ft.add(R.id.container, bookProgressFragment);
        ft.hide(myProfileFragment);
        ft.addToBackStack(bookProgressFragment.toString());
        //ft.show(bookProgressFragment);
        ft.commit();
        //thisFrag = "bookProgress";
        invalidateOptionsMenu();
    }

    @Override
    public void OnFollowingList() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        followingListFragment = new FollowingListFragment();
        followingListFragment.setDelegate(this);
        ft.add(R.id.container, followingListFragment);
        ft.hide(myProfileFragment);
        ft.addToBackStack(myProfileFragment.toString());
        //ft.show(followingListFragment);
        ft.commit();
        //thisFrag = "followingList";
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
        ft.add(R.id.container, updateBookProgressFragment);
        ft.hide(bookProgressFragment);
        ft.addToBackStack(updateBookProgressFragment.toString());
        //ft.show(updateBookProgressFragment);
        ft.commit();
        //thisFrag = "updateProgress";
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
        ft.add(R.id.container, othersReviewFragment);
        ft.hide(bookProgressFragment);
        ft.addToBackStack(othersReviewFragment.toString());
        //ft.show(othersReviewFragment);
        ft.commit();
        //thisFrag = "othersReview";
        invalidateOptionsMenu();
    }

    @Override
    public void OnJoinRebook() {
        Log.d("TAG", "User");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        joinRebookFragment = new JoinRebookFragment();
        joinRebookFragment.setDelegate(this);
        ft.add(R.id.container, joinRebookFragment);
        ft.hide(loginFragment);
        ft.addToBackStack(loginFragment.toString());
        //ft.show(othersReviewFragment);
        ft.commit();
        //thisFrag = "othersReview";
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
        ft.add(R.id.container, joinRebookFragment2);
        ft.hide(joinRebookFragment);
        ft.addToBackStack(joinRebookFragment.toString());
        //ft.show(othersReviewFragment);
        ft.commit();
        //thisFrag = "othersReview";
        invalidateOptionsMenu();
    }

    //@Override
    /*public void OnMyProfileFirst(User user) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        myProfileFragment = new MyProfileFragment();
        myProfileFragment.setDelegate(this);
        ft.add(R.id.container, myProfileFragment);
        ft.hide(joinRebookFragment2);
        ft.addToBackStack(joinRebookFragment2.toString());
        //ft.show(myProfileFragment);
        //thisFrag = "myProfile";
        ft.commit();
        invalidateOptionsMenu();
        Log.d("TAG", "on my profile");
    }*/

    @Override
    public void onCancel() {
        //int count = getFragmentManager().getBackStackEntryCount();
        /*if(getFragmentManager().getBackStackEntryAt(count - 1).getName().equals("myProfile"))
            thisFrag = "myProfile";
        else
            thisFrag = "newsfeed";*/
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onSave() {
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
    }
}
