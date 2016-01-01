package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.PostDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 30-Dec-15.
 */
public class NewsFeedFragment extends Fragment {

    public interface NewsFeedFragmentDelegate{}

    NewsFeedFragmentDelegate delegate;
    public void setDelegate(NewsFeedFragmentDelegate delegate){
        this.delegate = delegate;
    }

    ListView list;
    List<Post> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_fragment, container, false);

        list = (ListView) view.findViewById(R.id.newsFeedList);
        data = PostDB.getInstance().getAllPosts();
        CustomAdapter adapter = new CustomAdapter();
        list.setAdapter(adapter);


        return view;
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
            TextView userName = (TextView) convertView.findViewById(R.id.userProfileName);
            TextView bookName = (TextView) convertView.findViewById(R.id.bookName);
            TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            //ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

            Post post = data.get(position);
            userName.setText(post.getUserID());
            bookName.setText(post.getBookID());
            bookReview.setText(post.getText());

            return convertView;
        }
    }
}
