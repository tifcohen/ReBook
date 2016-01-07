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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class OtherProfileFragment extends Fragment{
    interface OtherProfileFragmentDelegate{

    }

    OtherProfileFragmentDelegate delegate;
    public void setDelegate(OtherProfileFragmentDelegate delegate){this.delegate=delegate;}

    public OtherProfileFragment(){

    }

    ListView myReadingList;
    List<Book> myReadingData;
    ListView myFollowingList;
    List<User> myFollowingData;
    ListView myBookShelfList;
    List<Book> myBookShelfData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);

        myReadingList = (ListView) view.findViewById(R.id.myReadingList);
        myReadingData = BookDB.getInstance().getAllBooks();
        MyBooksAdapter booksAdapter = new MyBooksAdapter();
        myReadingList.setAdapter(booksAdapter);

        /*myReadingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "row selected" + position);
                Book bk = myReadingData.get(position);
                if (delegate != null) {
                    delegate.OnBookProgress(bk);
                }
            }
        });*/

        myFollowingList = (ListView) view.findViewById(R.id.myFollowingList);
        myFollowingData = UserDB.getInstance().getAllUsers();
        MyFollowingAdapter followingAdapter = new MyFollowingAdapter();
        myFollowingList.setAdapter(followingAdapter);

        /*myFollowingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "row selected" + position);
                //User ur = myFollowingData.get(position);
                if (delegate != null) {
                    delegate.OnFollowingList();
                }
            }
        });*/

        myBookShelfList = (ListView) view.findViewById(R.id.myBookShelfList);
        myBookShelfData = BookDB.getInstance().getAllBooks();
        MyShelfAdapter bookShelfAdapter = new MyShelfAdapter();
        myBookShelfList.setAdapter(bookShelfAdapter);

        /*myBookShelfList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "row selected" + position);
                Book bk = myBookShelfData.get(position);
                if (delegate != null) {
                    delegate.OnBookProgress(bk);
                }
            }
        });
*/
        return view;
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

            Book book = myReadingData.get(position);
            bookName.setText(book.getBookName());

            return convertView;
        }
    }
}