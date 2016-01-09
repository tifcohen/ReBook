package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.R;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class UpdateBookProgressFragment extends Fragment {

    public interface UpdateBookProgressFragmentDelegate{
        //void OnAddNewBook();
    }

    String[] items = new String[]{"0 Stars"
                                ,"0.5 Stars"
                                ,"1 Star"
                                ,"1.5 Stars"
                                ,"2 Stars"
                                ,"2.5 Stars"
                                ,"3 Stars"
                                ,"3.5 Stars"
                                ,"4 Stars"
                                ,"4.5 Stars"
                                ,"5 Stars"};


    ListView list;
    List<Book> data;
    Book book;
    Post post;

    UpdateBookProgressFragmentDelegate delegate;
    public void setDelegate(UpdateBookProgressFragmentDelegate delegate){ this.delegate = delegate;}

    public UpdateBookProgressFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_progress_fragment, container, false);
        post = new Post("", "", "", "", null, 0, false, 0);
        if(book!=null){
            TextView bookName = (TextView) view.findViewById(R.id.bookProgressName);
            TextView bookAuthor = (TextView) view.findViewById(R.id.bookProgressAuthor);
            TextView bookPages = (TextView) view.findViewById(R.id.outOfPages);
            ImageView bookImage = (ImageView) view.findViewById(R.id.bookProgressImage);
            EditText currentPage = (EditText) view.findViewById(R.id.currentPage);
            EditText currentReview = (EditText) view.findViewById(R.id.myCurrentReviewText);
            Spinner dropdown = (Spinner) view.findViewById(R.id.dropdown);

            ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
            dropdown.setAdapter(adapter);

            bookName.setText(this.book.getBookName());
            bookAuthor.setText(this.book.getAuthor());
            int pages = this.book.getPages();
            bookPages.setText(" of " + pages + ".");

            post.setBookID(book.getBookID());
            post.setUserID(ParseUser.getCurrentUser().toString());
            post.setCurrentPage(Integer.getInteger(currentPage.getText().toString()));
            post.setGrade(Integer.getInteger(dropdown.getSelectedItem().toString()));
            post.setText(currentReview.getText().toString());
        }


        return view;
    }

    public void setBook(Book book) { this.book = book;}

}
