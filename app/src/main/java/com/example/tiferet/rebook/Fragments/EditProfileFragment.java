package com.example.tiferet.rebook.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Picker.DateEditText;
import com.example.tiferet.rebook.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TIFERET on 07-Jan-16.
 */
public class EditProfileFragment extends Fragment{
    ImageView imageView;
    String imageFileName = null;

    public interface EditProfileFragmentDelegate{
        void onSave();
        void onLogout();
        void onNewsFeed();
        void onCancel();
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
        imageView = (ImageView) view.findViewById(R.id.editProfileImage);
        final EditText fName = (EditText) view.findViewById(R.id.editUserFName);
        final EditText lName = (EditText) view.findViewById(R.id.editUserLName);
        final DateEditText birthDate = (DateEditText) view.findViewById(R.id.editUserBirthDate);
        final EditText addProfilePic = (EditText) view.findViewById(R.id.addProfilePic);

        fName.setText(user.getfName());
        lName.setText(user.getlName());

        if (TextUtils.isEmpty(user.getProfPicture()))
        {
            imageView.setImageResource(R.drawable.default_user);
            addProfilePic.setText("");
        }
        else
        {
            addProfilePic.setText(user.getProfPicture());
            Model.getInstance().loadImage(user.getProfPicture(), new Model.LoadImageListener() {
                @Override
                public void onResult(Bitmap imageBmp) {
                    if (imageBmp != null) {
                        imageView.setImageBitmap(imageBmp);
                    }
                }
            });
        }

        birthDate.setText(user.getBirthDate());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPicture();
            }
        });

        Button saveBtn = (Button) view.findViewById(R.id.saveEditUserBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ParseUser editUser = ParseUser.getCurrentUser();
                    editUser.put("fName", fName.getText().toString());
                    editUser.put("lName", lName.getText().toString());

                    if (!TextUtils.isEmpty(imageFileName))
                    {
                        editUser.put("imageName",imageFileName);
                    }
                    else
                    {
                        if (!TextUtils.isEmpty(addProfilePic.getText().toString())){
                            editUser.put("imageName",addProfilePic.getText().toString());
                        }
                        else
                        {
                            editUser.put("imageName","");
                        }
                    }
                    editUser.put("birthday", birthDate.getText().toString());
                    editUser.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                delegate.onSave();
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
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = "JPEG_" + timeStamp + ".jpeg";
            Model.getInstance().saveImage(imageBitmap,imageFileName);
        }
    }

    private void takingPicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void setUser(User user){this.user = user;}
}
