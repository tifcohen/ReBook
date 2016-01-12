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

import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.PostDB;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class FollowingListFragment extends Fragment {

    public interface FollowingListFragmentDelegate{
        void onLogout();
        void onNewsFeed();
    }

    FollowingListFragmentDelegate delegate;
    public void setDelegate(FollowingListFragmentDelegate delegate){
        this.delegate = delegate;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
    String currentId = ParseUser.getCurrentUser().getObjectId();
    ArrayList<User> data;
    ListView list;
    //List<User> data;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_list_fragment, container, false);

        list = (ListView) view.findViewById(R.id.followingList);
        //data = UserDB.getInstance().getAllUsers();
        CustomAdapter adapter = new CustomAdapter();
        list.setAdapter(adapter);

        return view;
    }

    public void setUser(User user){this.user = user;}


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

            final Button follow = (Button) convertView.findViewById(R.id.unfollowBtn);
            final User user = data.get(position);

            if (user.getUserId().equals(currentId))
                follow.setVisibility(View.GONE);

            if (Model.getInstance().amIFollowing(user.getUserId())) {
                boolean amIFollowing = Model.getInstance().amIFollowing(user.getUserId());
                if (amIFollowing) {
                    follow.setVisibility(View.VISIBLE);
                    follow.setText("Unfollow");
                }
            }

            TextView first = (TextView) convertView.findViewById(R.id.userFirstName);
            TextView last = (TextView) convertView.findViewById(R.id.userLastName);
            TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            ImageView image = (ImageView) convertView.findViewById(R.id.userProfileImage);
            first.setText(user.getfName() + " ");
            last.setText(user.getlName());

            follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean amIFollowing = Model.getInstance().amIFollowing(user.getUserId());
                    if (amIFollowing) {
                        Model.getInstance().stopFollowing(user.getUserId());
                        follow.setText("Follow");

                    }
                    else {
                        Model.getInstance().startFollowing(user.getUserId());
                        follow.setText("Unfollow");
                    }
                }
            });
            return convertView;
        }
    }
}
