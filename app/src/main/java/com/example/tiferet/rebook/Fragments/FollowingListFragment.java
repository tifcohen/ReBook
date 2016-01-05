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

import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.PostDB;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class FollowingListFragment extends Fragment {

    public interface FollowingListFragmentDelegate{

    }

    FollowingListFragmentDelegate delegate;
    public void setDelegate(FollowingListFragmentDelegate delegate){
        this.delegate = delegate;
    }

    ListView list;
    List<User> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_feed_fragment, container, false);

        list = (ListView) view.findViewById(R.id.followingList);
        data = UserDB.getInstance().getAllUsers();
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
                convertView = inflater.inflate(R.layout.following_single_row, null);
            }

            TextView first = (TextView) convertView.findViewById(R.id.userFirstName);
            TextView last = (TextView) convertView.findViewById(R.id.userLastName);
            TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            ImageView image = (ImageView) convertView.findViewById(R.id.userProfileImage);

            User user = data.get(position);
            first.setText(user.getfName());
            last.setText(user.getlName());

            return convertView;
        }
    }
}