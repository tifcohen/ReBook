package com.example.tiferet.rebook.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TIFERET on 04-Jan-16.
 */
public class AddNewBookFragment extends Fragment {
    ImageView newImage;
    String imageFileName;
    EditText newImageName;

    public interface AddNewBookFragmentDelegate{
        void onCancel();
        void onSave();
        void onLogout();
        void onNewsFeed();
    }

    AutoCompleteTextView bookNameList;
    int flag = 0;
    Book newBook;
    AddNewBookFragmentDelegate delegate;
    public void setDelegate(AddNewBookFragmentDelegate delegate){ this.delegate = delegate;}

    public AddNewBookFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_book_fragment, container, false);
        newImage = (ImageView) view.findViewById(R.id.addNewBookImage);
        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takingPicture();
            }
        });
        final EditText newAuthor = (EditText) view.findViewById(R.id.addNewBookAuthor);
        final EditText newPages = (EditText) view.findViewById(R.id.addNewBookPages);
        newImageName = (EditText) view.findViewById(R.id.addNewBookImageName);

        bookNameList = (AutoCompleteTextView) view.findViewById(R.id.autoComplete);
        Model.getInstance().getAllBooks(new Model.GetBooksListener() {
            @Override
            public void onBooksArrived(final ArrayList<Book> books) {
                String[] list = new String[books.size()];
                for (int i = 0; i < books.size(); i++) {
                    list[i] = books.get(i).getBookName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, list);
                bookNameList.setAdapter(adapter);
                bookNameList.setThreshold(1);
                newBook = new Book("","","",0,"");
                bookNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                        String selection = (String) parent.getItemAtPosition(position);
                        Log.d("TAG", selection);
                        newBook.setBookName(selection);
                        for(Book bk : books){
                            if(bk.getBookName().equals(selection)){
                                flag = 1;
                                newAuthor.setText(bk.getAuthor());
                                newPages.setText("" + bk.getPages());
                                newImageName.setText(bk.getPicture());
                                newBook.setBookID(bk.getBookID());
                                newBook.setAuthor(bk.getAuthor());
                                newBook.setPages(bk.getPages());
                                if (bk.getPicture() != null)
                                {
                                    Model.getInstance().loadImage(bk.getPicture(), new Model.LoadImageListener() {
                                        @Override
                                        public void onResult(Bitmap imageBmp) {
                                            newImage.setImageBitmap(imageBmp);
                                        }
                                    });
                                }
                                break;
                            }
                        }
                    }
                });
            }
        });



        Button saveBtn = (Button) view.findViewById(R.id.saveNewBookBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelNewBookBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    newBook.setBookName(bookNameList.getText().toString());
                    newBook.setAuthor(newAuthor.getText().toString());
                    newBook.setPages(Integer.parseInt(newPages.getText().toString()));
                    newBook.setPicture(newImageName.getText().toString());
                    Model.getInstance().addBook(newBook);
                }
                else {
                    Model.getInstance().addBookToUser(newBook);
                }
                delegate.onSave();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Canceling adding of Student");
                if (delegate != null)
                    delegate.onCancel();
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
            newImage.setImageBitmap(imageBitmap);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = "JPEG_" + timeStamp + ".jpeg";
            newImageName.setText(imageFileName);
            Model.getInstance().saveImage(imageBitmap,imageFileName);
        }
    }

    private void takingPicture(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
