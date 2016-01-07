package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.ModelParse;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.PostDB;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 30-Dec-15.
 */
public class NewsFeedFragment extends Fragment  {

    public interface NewsFeedFragmentDelegate{
        void OnSinglePost(Post post);
        void OnMyProfile();
    }

    NewsFeedFragmentDelegate delegate;
    public void setDelegate(NewsFeedFragmentDelegate delegate){
        this.delegate = delegate;
    }

    ListView list;
    List<Post> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_fragment, container, false);

        list = (ListView) view.findViewById(R.id.newsFeedList);
        data = Model.getInstance().getAllPosts();
        CustomAdapter adapter = new CustomAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "row selected" + position);
                Post post = data.get(position);
                if (delegate != null) {
                    delegate.OnSinglePost(post);
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "My Profile Pressed");
        switch (item.getItemId()) {
            case R.id.myProfileBtn:
                delegate.OnMyProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        activity.menuIdToDisplay = R.menu.menu_news_feed;
        activity.invalidateOptionsMenu();
    }

    class CustomAdapter extends BaseAdapter {

        public CustomAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return data.size();
        }

        @Override
        public Object getItem(int position) { //returns the post
            return data.get(position);
        }

        @Override
        public long getItemId(int position) { //returns post id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.news_feed_single_row, null);
            }
            final TextView userName = (TextView) convertView.findViewById(R.id.userProfileName);
            final TextView bookName = (TextView) convertView.findViewById(R.id.bookName);
            TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            //ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

            final Post post = data.get(position);
            Model.getInstance().getBookByIdAsync(post.getBookID(), new Model.GetBookListener() {
                @Override
                public void onBookArrived(Book book) {
                    bookName.setText(book.getBookName());

                }
            });

            Model.getInstance().getUserByIdAsync(post.getUserID(), new Model.GetUserListener() {
                @Override
                public void onUserArrived(User user) {
                    userName.setText(user.getfName() + " " + user.getlName());
                }
            });


            //bookName.setText(post.getBookID());
            bookReview.setText(post.getText());
            return convertView;
        }
    }
}
