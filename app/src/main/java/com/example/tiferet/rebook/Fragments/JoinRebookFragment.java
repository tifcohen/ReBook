package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;

/**
 * Created by TIFERET on 06-Jan-16.
 */
public class JoinRebookFragment extends Fragment {
    public interface JoinRebookFragmentDelegate{
        void OnJoinRebook(User user);
    }

    User user;

    JoinRebookFragmentDelegate delegate;
    public void setDelegate(JoinRebookFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public JoinRebookFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.join_rebook_fragment, container, false);
        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delegate!=null){
                    delegate.OnJoinRebook(user);
                }
            }
        });
        return view;
    }


    public void setUser(User user) {this.user = user;}
}
