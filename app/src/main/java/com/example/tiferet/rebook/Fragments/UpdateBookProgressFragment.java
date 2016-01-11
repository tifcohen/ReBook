package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
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
        void onSave();
        void OnAddNewBook();
        void onLogout();
        void onNewsFeed();
    }

    String[] items = new String[]{"0"
                                ,"1"
                                ,"2"
                                ,"3"
                                ,"4"
                                ,"5"};
    String[] isFinished = new String[]{"No", "Yes"};
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
            final EditText currentPage = (EditText) view.findViewById(R.id.currentPage);
            final EditText currentReview = (EditText) view.findViewById(R.id.myCurrentReviewText);
            final Spinner rate = (Spinner) view.findViewById(R.id.dropdown);
            final Spinner finished = (Spinner) view.findViewById(R.id.isFinished);

            ArrayAdapter<String>rateAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
            rate.setAdapter(rateAdapter);

            final ArrayAdapter<String>finishAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isFinished);
            finished.setAdapter(finishAdapter);

            bookName.setText(this.book.getBookName());
            bookAuthor.setText(this.book.getAuthor());
            int pages = this.book.getPages();
            bookPages.setText(" of " + pages + ".");

            Button save = (Button) view.findViewById(R.id.saveProgress);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int grade = setGrade(Integer.parseInt(rate.getSelectedItem().toString()));
                    post.setBookID(book.getBookID());
                    post.setUserID(ParseUser.getCurrentUser().toString());
                    post.setCurrentPage(Integer.parseInt(currentPage.getText().toString()));
                    post.setGrade(grade);
                    post.setText(currentReview.getText().toString());
                    if(finished.getSelectedItem().toString().equals("No")){
                        post.setFinished(false);
                    }
                    else {
                        post.setFinished(true);
                    }
                    Model.getInstance().addPost(post);
                    Model.getInstance().updateReadStatus(post);
                    delegate.onSave();
                }
            });
        }
        return view;
    }

    public void setBook(Book book) { this.book = book;}

    public int setGrade(int star){
        int grade = star*2;
        return grade;
    }

}
