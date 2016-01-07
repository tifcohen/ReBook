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
import android.widget.EditText;
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
        void OnJoinRebook2(User user);
        void onCancel();
    }

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

        final EditText email = (EditText) view.findViewById(R.id.addUserEmail);
        final EditText psw = (EditText) view.findViewById(R.id.addUserPassword);
        final EditText rpsw = (EditText) view.findViewById(R.id.addRepeatPassword);

        final Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!psw.getText().toString().equals(rpsw.getText().toString())){
                    //error message!
                }
                final User newUser = new User(email.toString(), psw.toString(), "", "", "", "");
                if(delegate!=null){
                    delegate.OnJoinRebook2(newUser);
                }
            }
        });

        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delegate!=null){
                    delegate.onCancel();
                }
            }
        });

        return view;
    }
}
