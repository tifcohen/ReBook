package com.example.tiferet.rebook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends Activity {

    ListView list;
    List<Post> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        list = (ListView) findViewById(R.id.newsFeedList);
        data = Model.getInstance().getAllPosts();
        CustomAdapter adapter = new CustomAdapter();
        list.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "My Profile Pressed");
        switch (item.getItemId()) {
            case R.id.myProfileBtn:{
                onMyProfile();
                Log.d("TAG", "finished mt profile");
                return true;
            }
            case R.id.action_logout: {
                onLogout();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_feed, menu);
        return true;
    }

    private void onLogout() {
        ParseUser.logOut();
        Log.d("TAG", "on log out");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onMyProfile() {
        User user = new User(ParseUser.getCurrentUser());
        String userId = user.getUserId();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
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
                LayoutInflater inflater = getLayoutInflater();
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
