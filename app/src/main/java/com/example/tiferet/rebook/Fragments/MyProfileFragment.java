package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TIFERET on 03-Jan-16.
 */
public class MyProfileFragment extends Fragment {
    public interface MyProfileFragmentDelegate{
        void OnAddNewBook();
        void OnBookProgress(String userId, Book book);
        void OnFollowingList(ArrayList<User> followers);
        void OnEditProfile(User user);
    }

    User currentUser;
    User user;
    ListView myReadingList;
    ArrayList<Book> myReadingData;
    ListView myFollowingList;
    ArrayList<User> myFollowingData;
    //ArrayList<User> followingList;
    ListView myBookShelfList;
    List<Book> myBookShelfData;

    MyProfileFragmentDelegate delegate;
    public void setDelegate(MyProfileFragmentDelegate delegate){ this.delegate = delegate;}

    public MyProfileFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);
        final Button edit = (Button) view.findViewById(R.id.editProfile);
        final TextView followersTextView = (TextView) view.findViewById(R.id.myProfileFollowers);
        final TextView nameTextView = (TextView) view.findViewById(R.id.myProfileUsername);

        Model.getInstance().getUserByIdAsync(ParseUser.getCurrentUser().getObjectId(), new Model.GetUserListener() {
            @Override
            public void onUserArrived(User user) {
                //currentUser = user;
                nameTextView.setText(user.getfName() + " " + user.getlName());
                edit.setText("Edit " + user.getUsername());

            }
        });

        Model.getInstance().getFollowersList(ParseUser.getCurrentUser().getObjectId(), new Model.GetFollowersListener() {
            @Override
            public void onFollowersArrived(ArrayList<User> followers) {
                String followersAmount = (followers.size() > 0) ? (followers.size() > 1) ? followers.size() + " Followers" : followers.size() + " Follower" : "No Followers";
                followersTextView.setText(followersAmount);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.OnEditProfile(user);
            }
        });


        myReadingList = (ListView) view.findViewById(R.id.myReadingList);
        Model.getInstance().getReadingStatusAsync(ParseUser.getCurrentUser().getObjectId(), false, new Model.GetReadingStatusListener() {
            @Override
            public void onReadingStatusArrived(ArrayList<Book> bookList) {
                myReadingData = bookList;
                MyBooksAdapter booksAdapter = new MyBooksAdapter();
                myReadingList.setAdapter(booksAdapter);
                myReadingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("TAG", "row selected" + position);
                        Book book = myReadingData.get(position);
                        if (delegate != null) {
                            String userId = ParseUser.getCurrentUser().getObjectId();
                            delegate.OnBookProgress(userId,book);
                        }
                    }
                });
            }
        });


        myFollowingList = (ListView) view.findViewById(R.id.myFollowingList);
        Model.getInstance().getFollowingListByIdAsync(ParseUser.getCurrentUser().getObjectId(), new Model.GetFollowingListener() {
            @Override
            public void onFollowingListArrived(ArrayList<User> followers) {
                myFollowingData = followers;
                MyFollowingAdapter followingAdapter = new MyFollowingAdapter();
                myFollowingList.setAdapter(followingAdapter);
                myFollowingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("TAG", "row selected" + position);
                        //User ur = myFollowingData.get(position);
                        if (delegate != null) {
                            delegate.OnFollowingList(myFollowingData);
                        }
                    }
                });
            }
        });

        myBookShelfList = (ListView) view.findViewById(R.id.myBookShelfList);
        myBookShelfData = BookDB.getInstance().getAllBooks();


        Model.getInstance().getReadingStatusAsync(ParseUser.getCurrentUser().getObjectId(), true, new Model.GetReadingStatusListener() {
            @Override
            public void onReadingStatusArrived(ArrayList<Book> bookList) {
                myBookShelfData = bookList;
                MyShelfAdapter bookShelfAdapter = new MyShelfAdapter();
                myBookShelfList.setAdapter(bookShelfAdapter);
            }
        });
        return view;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "onOptionsItemSelected was pressed");
        switch (item.getItemId()) {
            case R.id.addBookkBtn : {
                delegate.OnAddNewBook();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseUser curr = ParseUser.getCurrentUser();
        User temp = new User(curr);
        setUser(temp);
        MainActivity activity = (MainActivity) getActivity();
        activity.menuIdToDisplay = R.menu.menu_add_book;
        activity.invalidateOptionsMenu();
    }


    class MyBooksAdapter extends BaseAdapter {

        public MyBooksAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return myReadingData.size();
        }

        @Override
        public Object getItem(int position) { //returns the post
            return myReadingData.get(position);
        }

        @Override
        public long getItemId(int position) { //returns post id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_book_single_row, null);
            }
            TextView bookName = (TextView) convertView.findViewById(R.id.myBookName);
            ImageView bookImage = (ImageView) convertView.findViewById(R.id.myBookImage);
            ImageView bookProgress = (ImageView) convertView.findViewById(R.id.bookProgress);

            Book book = myReadingData.get(position);
            bookName.setText(book.getBookName());

            return convertView;
        }
    }

    class MyFollowingAdapter extends BaseAdapter {

        public MyFollowingAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return myFollowingData.size();
        }

        @Override
        public Object getItem(int position) { //returns the post
            return myFollowingData.get(position);
        }

        @Override
        public long getItemId(int position) { //returns post id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_following_list_single_column, null);
            }
            ImageView followingImage = (ImageView) convertView.findViewById(R.id.followingImage);

            User user = myFollowingData.get(position);
            return convertView;
        }
    }

    class MyShelfAdapter extends BaseAdapter {

        public MyShelfAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return myBookShelfData.size();
        }

        @Override
        public Object getItem(int position) { //returns the post
            return myBookShelfData.get(position);
        }

        @Override
        public long getItemId(int position) { //returns post id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.my_book_shelf_single_column, null);
            }
            TextView bookName = (TextView) convertView.findViewById(R.id.bookShelfName);
            ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookShelfImage);

            Book book = myBookShelfData.get(position);
            bookName.setText(book.getBookName());

            return convertView;
        }
    }
}
