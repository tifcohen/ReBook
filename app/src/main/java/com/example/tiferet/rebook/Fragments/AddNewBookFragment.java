package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TIFERET on 04-Jan-16.
 */
public class AddNewBookFragment extends Fragment {
    public interface AddNewBookFragmentDelegate{
        void onCancel();
        void onSave();
    }

    ArrayList<Book> data;
    AutoCompleteTextView bookNameList;

    AddNewBookFragmentDelegate delegate;
    public void setDelegate(AddNewBookFragmentDelegate delegate){ this.delegate = delegate;}

    public AddNewBookFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_book_fragment, container, false);

        //data = BookDB.getInstance().getAllBooks();
        bookNameList = (AutoCompleteTextView) view.findViewById(R.id.autoComplete);
        Model.getInstance().getAllBooks(new Model.GetBooksListener() {
            @Override
            public void onBooksArrived(ArrayList<Book> books) {
                String[] list = new String[books.size()];
                for (int i = 0; i < books.size(); i++) {
                    list[i] = books.get(i).getBookName();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, list);
                bookNameList.setAdapter(adapter);
                bookNameList.setThreshold(1);
            }
        });


        //final EditText newName = (EditText) view.findViewById(R.id.addNewBookName);
        final EditText newAuthor = (EditText) view.findViewById(R.id.addNewBookAuthor);
        final EditText newPages = (EditText) view.findViewById(R.id.addNewBookPages);
        final EditText newPicture = (EditText) view.findViewById(R.id.addNewBookPicture);

        Button saveBtn = (Button) view.findViewById(R.id.saveNewBookBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelNewBookBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Book newBk = new Book("", newName.getText().toString(),
                //        newAuthor.getText().toString(), new Integer(newPages.getText().toString()), "");
                Book newBk = new Book("", "book name",
                        newAuthor.getText().toString(), new Integer(newPages.getText().toString()), "");
                BookDB.getInstance().addBook(newBk);
                delegate.onSave();
                //Alert alert = new Alert("" + name.getText().toString() + " Was Added Successfully :)", "Dismiss", delegate);
                //alert.show(getFragmentManager(), "TAG");
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

}
