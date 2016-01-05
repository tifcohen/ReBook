package com.example.tiferet.rebook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tiferet.rebook.Fragments.AddNewBookFragment;
import com.example.tiferet.rebook.Fragments.BookProgressFragment;
import com.example.tiferet.rebook.Fragments.MainActivityFragment;
import com.example.tiferet.rebook.Fragments.MyProfileFragment;
import com.example.tiferet.rebook.Fragments.NewsFeedFragment;
import com.example.tiferet.rebook.Fragments.OthersReviewFragment;
import com.example.tiferet.rebook.Fragments.SinglePostFragment;
import com.example.tiferet.rebook.Fragments.UpdateBookProgressFragment;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Post;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MainActivityFragmentDelegate,
        NewsFeedFragment.NewsFeedFragmentDelegate, SinglePostFragment.SinglePostFragmentDelegate,
        MyProfileFragment.MyProfileFragmentDelegate, AddNewBookFragment.AddNewBookFragmentDelegate,
        BookProgressFragment.BookProgressFragmentDelegate, UpdateBookProgressFragment.UpdateBookProgressFragmentDelegate,
        OthersReviewFragment.OthersReviewFragmentDelegate{

    String thisFrag = "login";
    MainActivityFragment loginFragment;
    NewsFeedFragment newsFeedFragment;
    SinglePostFragment singlePostFragment;
    MyProfileFragment myProfileFragment;
    AddNewBookFragment addNewBookFragment;
    BookProgressFragment bookProgressFragment;
    UpdateBookProgressFragment updateBookProgressFragment;
    OthersReviewFragment othersReviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginFragment = (MainActivityFragment) getFragmentManager().findFragmentById(R.id.loginFragment);
        newsFeedFragment = (NewsFeedFragment) getFragmentManager().findFragmentById(R.id.newsFeedFragment);
        singlePostFragment = (SinglePostFragment) getFragmentManager().findFragmentById(R.id.singlePostFragment);
        myProfileFragment = (MyProfileFragment) getFragmentManager().findFragmentById(R.id.myProfileFragment);
        addNewBookFragment = (AddNewBookFragment) getFragmentManager().findFragmentById(R.id.addNewBookFragment);
        bookProgressFragment = (BookProgressFragment) getFragmentManager().findFragmentById(R.id.bookProgressFragment);
        updateBookProgressFragment = (UpdateBookProgressFragment) getFragmentManager().findFragmentById(R.id.updateBookProgressFragment);
        othersReviewFragment = (OthersReviewFragment) getFragmentManager().findFragmentById(R.id.othersReviewFragment);

        loginFragment.setDelegate(this);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.hide(newsFeedFragment);
        ft.hide(singlePostFragment);
        ft.hide(myProfileFragment);
        ft.hide(addNewBookFragment);
        ft.hide(bookProgressFragment);
        ft.hide(updateBookProgressFragment);
        ft.hide(othersReviewFragment);

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
        MenuItem item = menu.findItem(R.id.generalBtn);
        switch (thisFrag){
            case "login":
                item.setTitle("");
                break;
            case "myProfile":
                item.setIcon(android.R.drawable.ic_input_get);
            default:
                item.setTitle("My Profile");
                break;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "onOptionsItemSelected was pressed");

        switch (item.getItemId()) {
            case R.id.generalBtn : {
                if (thisFrag.equals("myProfile")) {
                    OnAddNewBook();
                }
                if (thisFrag.equals("newsfeed")){
                    OnMyProfile();
                }
            }
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
    public void OnSinglePost(Post post) {
        if (thisFrag.equals("newsfeed")){
            Log.d("TAG", "post selected " + post.getPostID());
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            singlePostFragment = new SinglePostFragment();
            singlePostFragment.setPost(post);
            singlePostFragment.setDelegate(this);
            ft.add(R.id.container, singlePostFragment);
            ft.hide(newsFeedFragment);
            ft.addToBackStack("newsfeed");
            ft.show(singlePostFragment);
            thisFrag = "singlePost";
            ft.commit();
            invalidateOptionsMenu();
        }

            Log.d("TAG","thisFrag is currently: " + thisFrag);

    }

    public void OnMyProfile() {
        if (thisFrag.equals("newsfeed")){
            Log.d("TAG", "on my profile");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            myProfileFragment = new MyProfileFragment();
            myProfileFragment.setDelegate(this);
            ft.add(R.id.container, myProfileFragment);
            ft.hide(newsFeedFragment);
            ft.addToBackStack("newsfeed");
            ft.show(myProfileFragment);
            thisFrag = "myProfile";
            ft.commit();
            invalidateOptionsMenu();
        }
        Log.d("TAG","thisFrag is currently: " + thisFrag);
    }

    @Override
    public void OnAddNewBook() {
        if (thisFrag.equals("myProfile")){
            Log.d("TAG", "on add new book");
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            addNewBookFragment = new AddNewBookFragment();
            addNewBookFragment.setDelegate(this);
            ft.add(R.id.container, addNewBookFragment);
            ft.hide(myProfileFragment);
            ft.addToBackStack("myProfile");
            ft.show(addNewBookFragment);
            thisFrag = "addNewBook";
            ft.commit();
            invalidateOptionsMenu();
        }
        Log.d("TAG","thisFrag is currently: " + thisFrag);

    }

    @Override
    public void OnBookProgress(Book book) {
        if (thisFrag.equals("myProfile")){
            Log.d("TAG", "Book selected " + book.getBookID());
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            bookProgressFragment = new BookProgressFragment();
            bookProgressFragment.setBook(book);
            bookProgressFragment.setDelegate(this);
            ft.add(R.id.container, bookProgressFragment);
            ft.hide(myProfileFragment);
            ft.addToBackStack("myProfile");
            ft.show(bookProgressFragment);
            ft.commit();
            thisFrag = "bookProgress";
            invalidateOptionsMenu();
        }
    }

    @Override
    public void OnUpdateProgress(Book book) {
        if (thisFrag.equals("bookProgress")){
            Log.d("TAG", "Book selected " + book.getBookID());
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            updateBookProgressFragment = new UpdateBookProgressFragment();
            updateBookProgressFragment.setBook(book);
            updateBookProgressFragment.setDelegate(this);
            ft.add(R.id.container, updateBookProgressFragment);
            ft.hide(bookProgressFragment);
            ft.addToBackStack("bookProgress");
            ft.show(updateBookProgressFragment);
            ft.commit();
            thisFrag = "updateProgress";
            invalidateOptionsMenu();
        }
    }

    @Override
    public void OnOthersReview(Book book) {
        if (thisFrag.equals("bookProgress")){
            Log.d("TAG", "Book selected " + book.getBookID());
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            othersReviewFragment = new OthersReviewFragment();
            othersReviewFragment.setBook(book);
            othersReviewFragment.setDelegate(this);
            ft.add(R.id.container, othersReviewFragment);
            ft.hide(bookProgressFragment);
            ft.addToBackStack("bookProgress");
            ft.show(othersReviewFragment);
            ft.commit();
            thisFrag = "othersReview";
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("DEBUG", "Back was pressed. thisFrag = " + thisFrag);
        int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            String name = getFragmentManager().getBackStackEntryAt(count - 1).getName();


            switch (thisFrag) {
                case "newsfeed":
                    thisFrag = "login";
                    break;
                case "singlePost":
                    thisFrag = "newsfeed";
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

    @Override
    public void onCancel() {
        int count = getFragmentManager().getBackStackEntryCount();
        if(getFragmentManager().getBackStackEntryAt(count - 1).getName().equals("myProfile"))
            thisFrag = "myProfile";
        else
            thisFrag = "newsfeed";
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onSave() {
        thisFrag = "myProfile";
        invalidateOptionsMenu();
        getFragmentManager().popBackStack();
    }

}
