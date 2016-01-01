package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 01-Jan-16.
 */
public class SinglePostFragment extends Fragment {
    public interface SinglePostFragmentDelegate{
        void OnSinglePost();
    }

    Post post;
    SinglePostFragmentDelegate delegate;
    public void setDelegate(SinglePostFragmentDelegate delegate){ this.delegate = delegate;}

    public SinglePostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_post_fragment, container, false);

        if(post!=null){
            TextView userName = (TextView) view.findViewById(R.id.userName);
            TextView bookName = (TextView) view.findViewById(R.id.bookName);
            TextView bookReview = (TextView) view.findViewById(R.id.bookReview);

            userName.setText(this.post.getUserID());
            bookName.setText(this.post.getBookID());
            bookReview.setText(this.post.getText());
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(), "Post is null",Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
