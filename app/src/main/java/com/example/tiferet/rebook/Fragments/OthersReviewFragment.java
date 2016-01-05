package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.PostDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class OthersReviewFragment extends Fragment {
    public interface OthersReviewFragmentDelegate{
        //void OnAddNewBook();
    }

    ListView list;
    List<Post> data;
    Book book;
    Post post;

    OthersReviewFragmentDelegate delegate;
    public void setDelegate(OthersReviewFragmentDelegate delegate){ this.delegate = delegate;}

    public OthersReviewFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.others_review_fragment, container, false);
        if(book!=null){
            TextView bookName = (TextView) view.findViewById(R.id.bookProgressName);
            TextView bookAuthor = (TextView) view.findViewById(R.id.bookProgressAuthor);
            ImageView bookImage = (ImageView) view.findViewById(R.id.bookProgressImage);

            bookName.setText(this.book.getBookName());
            bookAuthor.setText(this.book.getAuthor());
        }

        list = (ListView) view.findViewById(R.id.reviewsList);
        data = PostDB.getInstance().getAllPosts();
        OthersReviewAdapter adapter = new OthersReviewAdapter();
        list.setAdapter(adapter);

        return view;
    }

    public void setBook(Book book) { this.book = book;}

    class OthersReviewAdapter extends BaseAdapter {

        public OthersReviewAdapter() {
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
                convertView = inflater.inflate(R.layout.others_review_single_row, null);
            }
            TextView userName = (TextView) convertView.findViewById(R.id.userProfileName);
            TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);

            Post post = data.get(position);
            userName.setText(post.getUserID());
            bookReview.setText(post.getText());
            return convertView;
        }
    }
}

