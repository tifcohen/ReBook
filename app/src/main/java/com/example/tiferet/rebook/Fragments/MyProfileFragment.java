package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;
import com.parse.ParseException;
import com.parse.ParseUser;

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
        void onLogout();
        void onNewsFeed();
    }

    User user;

    ListView myReadingList;
    ListView myFollowingList;
    ListView myBookShelfList;
    ArrayList<Book> myReadingData;
    ArrayList<User> myFollowingData;
    List<Book> myBookShelfData;
    ImageView myProfilePicture;
    ArrayList<Integer> myProgressData;
    TextView nameTextView;
    Button edit;
    ProgressBar spinner;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId;
    MyProfileFragmentDelegate delegate;
    public void setDelegate(MyProfileFragmentDelegate delegate){ this.delegate = delegate;}

    public MyProfileFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar3);
        spinner.setVisibility(View.VISIBLE);
        edit = (Button) view.findViewById(R.id.editProfile);
        final TextView followersTextView = (TextView) view.findViewById(R.id.myProfileFollowers);
        nameTextView = (TextView) view.findViewById(R.id.myProfileUsername);
        myProfilePicture = (ImageView) view.findViewById(R.id.myProfilePicture);
        //Toast.makeText(getActivity().getApplicationContext(),Model.getInstance().getLocalBooksCount()+ " Books ", Toast.LENGTH_LONG).show();

        if (TextUtils.isEmpty(userId)) {
            try {
                ParseUser.getCurrentUser().fetch();
                Toast.makeText(getActivity().getApplicationContext(), ParseUser.getCurrentUser().getString("fName"), Toast.LENGTH_LONG).show();
                userId = ParseUser.getCurrentUser().getObjectId();
                setUser(new User(ParseUser.getCurrentUser()));
                refreshPage(user);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinner.setVisibility(View.GONE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            Model.getInstance().getUserByIdAsync(userId, new Model.GetUserListener() {
                @Override
                public void onUserArrived(User user) {
                    setUser(user);
                    refreshPage(user);
                    spinner.setVisibility(View.GONE);
                }
            });
        }
        Model.getInstance().getFollowersList(userId, new Model.GetFollowersListener() {
            @Override
            public void onFollowersArrived(ArrayList<User> followers) {
                String followersAmount = (followers.size() > 0) ? (followers.size() > 1) ? followers.size() + " Followers" : followers.size() + " Follower" : "No Followers";
                followersTextView.setText(followersAmount);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId.equals(ParseUser.getCurrentUser().getObjectId())) {
                    delegate.OnEditProfile(user);
                } else {
                    boolean amIFollowing = Model.getInstance().amIFollowing(userId);
                    if (amIFollowing) {

                        Model.getInstance().stopFollowing(userId);
                        edit.setText("Follow " + user.getfName());

                    } else {
                        Model.getInstance().startFollowing(userId);
                        edit.setText("Unfollow " + user.getfName());
                    }
                }
            }
        });

        myReadingList = (ListView) view.findViewById(R.id.myReadingList);
        Model.getInstance().getReadingStatusAsync(userId, false, new Model.GetReadingStatusListener() {
            @Override
            public void onReadingStatusArrived(ArrayList<Book> bookList, ArrayList<Integer> progressList) {
                myReadingData = bookList;
                myProgressData = progressList;
                MyBooksAdapter booksAdapter = new MyBooksAdapter();
                myReadingList.setAdapter(booksAdapter);
                myReadingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (userId.equals(ParseUser.getCurrentUser().getObjectId())) {
                            Book book = myReadingData.get(position);
                            if (delegate != null) {
                                String userId = user.getUserId();
                                delegate.OnBookProgress(userId, book);
                            }
                        } else {
                            Book book = myReadingData.get(position);
                            if (delegate != null) {
                                String userId = "GLOBAL";
                                delegate.OnBookProgress(userId, book);
                            }
                        }
                    }
                });
            }
        });

        myFollowingList = (ListView) view.findViewById(R.id.myFollowingList);
        Model.getInstance().getFollowingListByIdAsync(userId, new Model.GetFollowingListener() {
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

        Model.getInstance().getReadingStatusAsync(userId, true, new Model.GetReadingStatusListener() {
            @Override
            public void onReadingStatusArrived(ArrayList<Book> bookList, ArrayList<Integer> progressList) {
                myBookShelfData = bookList;
                MyShelfAdapter booksAdapter = new MyShelfAdapter();
                myBookShelfList.setAdapter(booksAdapter);
                myBookShelfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (userId.equals(ParseUser.getCurrentUser().getObjectId())) {
                            Book book = myBookShelfData.get(position);
                            if (delegate != null) {
                                String userId = user.getUserId();
                                delegate.OnBookProgress(userId, book);
                            }
                        } else {
                            Book book = myBookShelfData.get(position);
                            if (delegate != null) {
                                String userId = "GLOBAL";
                                delegate.OnBookProgress(userId, book);
                            }
                        }
                    }
                });
            }
        });
        return view;
    }

    private void refreshPage(User user) {
        if (!TextUtils.isEmpty(user.getProfPicture()))
        {
            Model.getInstance().loadImage(user.getProfPicture(), new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    if (imageBmp != null) {
                        myProfilePicture.setImageBitmap(imageBmp);
                    }
                }
            });
        }
        else {
            myProfilePicture.setImageResource(R.drawable.default_image);
        }

        nameTextView.setText(user.getfName() + " " + user.getlName());
        if (userId.equals(ParseUser.getCurrentUser().getObjectId()))
            edit.setText("Edit My Details");
        else {
            boolean amIFollowing = Model.getInstance().amIFollowing(userId);
            Log.d("Debug", "Following:" + amIFollowing);
            if (amIFollowing) {

                edit.setText("Unfollow " + user.getfName());
            }
            else {
                edit.setText("Follow " + user.getfName());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
/*
        if (TextUtils.isEmpty(userId)) {
            try {
                ParseUser.getCurrentUser().fetch();
                Toast.makeText(getActivity().getApplicationContext(), ParseUser.getCurrentUser().getString("fName"), Toast.LENGTH_LONG).show();
                userId = ParseUser.getCurrentUser().getObjectId();
                setUser(new User(ParseUser.getCurrentUser()));
                refreshPage(user);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            Model.getInstance().getUserByIdAsync(userId, new Model.GetUserListener() {
                @Override
                public void onUserArrived(User user) {
                    setUser(user);
                    refreshPage(user);
                }
            });
        }*/
    }

    public void setUser(User user){
        this.user = user;
        Log.d("Debug", "User was set");
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
            ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.bookProgressBar);



            Book book = myReadingData.get(position);

            int percent = (int)((myProgressData.get(position) * 100.0f) / book.getPages());
            bookName.setText(book.getBookName() + " (" + percent + "%)");

            progressBar.setMax(book.getPages());
            progressBar.setProgress(myProgressData.get(position));
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
            final ImageView followingImage = (ImageView) convertView.findViewById(R.id.followingImage);

            User toFollow = myFollowingData.get(position);

            if (toFollow.getProfPicture() != null)
            {
                if (!toFollow.getProfPicture().equals(""))
                {
                    Model.getInstance().loadImage(toFollow.getProfPicture(), new Model.LoadImageListener() {
                        @Override
                        public void onResult(Bitmap imageBmp) {
                            if (imageBmp != null) {
                                followingImage.setImageBitmap(imageBmp);
                            }
                        }
                    });
                }
                else
                {
                    followingImage.setImageResource(R.drawable.default_user);
                }
            }
            else
            {
                followingImage.setImageResource(R.drawable.default_user);
            }
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
