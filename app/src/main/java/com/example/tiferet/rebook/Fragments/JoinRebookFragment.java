package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        final EditText username = (EditText) view.findViewById(R.id.join1UsernameEditText);
        final EditText psw = (EditText) view.findViewById(R.id.join1PasswordEditText);
        final EditText rpsw = (EditText) view.findViewById(R.id.join1RepeatPasswordEditText);

        final Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (psw.getText().toString().equals("") ||
                        rpsw.getText().toString().equals("") ||
                        username.getText().toString().equals(""))
                {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "All fields are required.", Toast.LENGTH_LONG)
                            .show();
                }
                else
                {
                    if(!psw.getText().toString().equals(rpsw.getText().toString())){
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Passwords do not match.", Toast.LENGTH_LONG)
                                .show();
                    }
                    else
                    {
                        final User tempUser = new User(psw.toString(),username.toString(), "", "", "", "", "");
                        if(delegate!=null){
                            delegate.OnJoinRebook2(tempUser);
                        }
                    }
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
