package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
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

        final EditText newAuthor = (EditText) view.findViewById(R.id.addNewBookAuthor);
        final EditText newPages = (EditText) view.findViewById(R.id.addNewBookPages);
        final EditText newPicture = (EditText) view.findViewById(R.id.addNewBookPicture);

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

                bookNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                        String selection = (String) parent.getItemAtPosition(position);
                        Log.d("TAG", selection);
                        newBook = new Book("",selection,"",0,"");
                        for(Book bk : books){
                            if(bk.getBookName().equals(selection)){
                                    flag = 1;
                                    newAuthor.setText(bk.getAuthor());
                                    newPages.setText("" + bk.getPages());
                                    //newPicture.setText(bk.getPicture());
                                    newBook.setBookID(bk.getBookID());
                                    newBook.setAuthor(bk.getAuthor());
                                    newBook.setPages(bk.getPages());
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
                    Model.getInstance().addBook(newBook);
                }
                Model.getInstance().addBookToUser(newBook);
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
