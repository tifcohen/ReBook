package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.R;

import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class UpdateBookProgressFragment extends Fragment {

    public interface UpdateBookProgressFragmentDelegate{
        //void OnAddNewBook();
    }

    ListView list;
    List<Book> data;
    Book book;

    UpdateBookProgressFragmentDelegate delegate;
    public void setDelegate(UpdateBookProgressFragmentDelegate delegate){ this.delegate = delegate;}

    public UpdateBookProgressFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_progress_fragment, container, false);
        if(book!=null){
            TextView bookName = (TextView) view.findViewById(R.id.bookProgressName);
            TextView bookAuthor = (TextView) view.findViewById(R.id.bookProgressAuthor);
            TextView bookPages = (TextView) view.findViewById(R.id.outOfPages);
            ImageView bookImage = (ImageView) view.findViewById(R.id.bookProgressImage);
            ProgressBar bookProgress = (ProgressBar) view.findViewById(R.id.progressBarBook);
            EditText currentPage = (EditText) view.findViewById(R.id.currentPage);
            EditText currentReview = (EditText) view.findViewById(R.id.myCurrentReviewText);

            bookName.setText(this.book.getBookName());
            bookAuthor.setText(this.book.getAuthor());
            int pages = this.book.getPages();
            bookPages.setText("out of " + pages + " pages");
        }

        return view;
    }

    public void setBook(Book book) { this.book = book;}

}
