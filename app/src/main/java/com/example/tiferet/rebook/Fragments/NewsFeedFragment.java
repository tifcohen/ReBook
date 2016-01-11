/*
package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
*/
/**
 * Created by TIFERET on 30-Dec-15.
 */
/*public class NewsFeedFragment extends Fragment  {

    public interface NewsFeedFragmentDelegate{
        //void OnSinglePost(Post post);
        void OnMyProfile(User user);
        void OnLogout();
    }

    NewsFeedFragmentDelegate delegate;
    public void setDelegate(NewsFeedFragmentDelegate delegate){
        this.delegate = delegate;
    }

    ListView list;
    List<Post> data;

    User user;

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

        /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void setUser(User user){
        this.user=user;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "My Profile Pressed");
        switch (item.getItemId()) {
            case R.id.myProfileBtn:
                User user = new User(ParseUser.getCurrentUser());
                delegate.OnMyProfile(user);
                return true;
            case R.id.action_logout:
                delegate.OnLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        Log.d("TAG","NF on resume");
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
            final TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            final ImageView stars = (ImageView) convertView.findViewById(R.id.stars);
            final TextView page = (TextView) convertView.findViewById(R.id.pageTextView);
            final TextView action = (TextView) convertView.findViewById(R.id.actionTextView);
            final TextView action2 = (TextView) convertView.findViewById(R.id.action2TextView);

            Model.getInstance().getPostsAsync(ParseUser.getCurrentUser().getObjectId(), new Model.GetPostsAsyncListener() {
                @Override
                public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray, ArrayList<Book> bookArray) {
                    Book book = bookArray.get(position);
                    User user = userArray.get(position);
                    Post post = postArray.get(position);
                    userName.setText(user.getfName() + " " + user.getlName());
                    userName.setTag(user);

                    if (book.getBookName().length() > 27) {
                        bookName.setText(book.getBookName().substring(0, 27) + "...");
                    }
                    else {
                        bookName.setText(book.getBookName());
                    }
                    bookName.setTag(post);


                    if (post.getCurrentPage() == 0 && post.getGrade() == 0) {
                        action.setText("Started ");
                        bookReview.setVisibility(View.GONE);
                        action2.setText("Not yet rated.");
                        page.setVisibility(View.GONE);
                        stars.setVisibility(View.GONE);
                        action2.setVisibility(View.GONE);

                    }
                    else
                    {
                        page.setVisibility(View.VISIBLE);
                        page.setText(" Page: " + post.getCurrentPage());
                        stars.setImageResource(book.getStars(post.getGrade()));
                        stars.setVisibility(View.VISIBLE);
                        action2.setVisibility(View.VISIBLE);
                        action2.setText("Rated ");
                        if (post.isFinished())
                            action.setText("finished ");
                        else
                            action.setText("is reading ");

                        if (post.getText().isEmpty())
                        {
                            bookReview.setVisibility(View.GONE);
                        }
                        else
                        {
                            bookReview.setText(post.getText());
                            bookReview.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            return convertView;
        }
    }


}
*/