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
import com.example.tiferet.rebook.Picker.DateEditText;
import com.example.tiferet.rebook.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by TIFERET on 07-Jan-16.
 */
public class EditProfileFragment extends Fragment{
    public interface EditProfileFragmentDelegate{
        void onSaveChanges();

        void onSave();
    }

    User user;
    EditProfileFragmentDelegate delegate;

    public void setDelegate(EditProfileFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    public EditProfileFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        final EditText fName = (EditText) view.findViewById(R.id.editUserFName);
        final EditText lName = (EditText) view.findViewById(R.id.editUserLName);
        final DateEditText birthDate = (DateEditText) view.findViewById(R.id.editUserBirthDate);

        fName.setText(user.getfName());
        lName.setText(user.getlName());
        birthDate.setText(user.getBirthDate());

        Button saveBtn = (Button) view.findViewById(R.id.saveEditUserBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ParseUser editUser = ParseUser.getCurrentUser();
                    editUser.put("fName", fName.getText().toString());
                    editUser.put("lName", lName.getText().toString());
                    editUser.put("birthday", birthDate.getText().toString());
                    editUser.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity().getApplicationContext(), "Edit", Toast.LENGTH_LONG).show();
                delegate.onSave();
            }
        });


        return view;
    }

    public void setUser(User user){this.user = user;}
}
