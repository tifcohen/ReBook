package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class BookProgressFragment extends Fragment {

    public interface BookProgressFragmentDelegate{
        //void OnAddNewBook();
    }

    ListView list;
    List<Post> data;
    Book book;

    BookProgressFragmentDelegate delegate;
    public void setDelegate(BookProgressFragmentDelegate delegate){ this.delegate = delegate;}

    public BookProgressFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_progress_fragment, container, false);

        return view;
    }

    public void setBook(Book book) { this.book = book;}
}
