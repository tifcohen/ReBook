package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.BookDB;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.Model.UserDB;
import com.example.tiferet.rebook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class BookProgressFragment extends Fragment {

    public interface BookProgressFragmentDelegate{
        void OnUpdateProgress(Book book);
        void OnOthersReview(Book book);
    }

    ListView list;
    ArrayList<Post> data;
    Book book;
    String userId;
    TextView bookPages;
    ProgressBar bookProgress;

    BookProgressFragmentDelegate delegate;
    public void setDelegate(BookProgressFragmentDelegate delegate){ this.delegate = delegate;}

    public BookProgressFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_progress_fragment, container, false);
        if(book!=null){
            TextView bookName = (TextView) view.findViewById(R.id.bookProgressName);
            TextView bookAuthor = (TextView) view.findViewById(R.id.bookProgressAuthor);
            bookPages = (TextView) view.findViewById(R.id.bookProgressPages);
            ImageView bookImage = (ImageView) view.findViewById(R.id.bookProgressImage);
            bookProgress = (ProgressBar) view.findViewById(R.id.progressBarBook);

            bookName.setText(this.book.getBookName());
            bookAuthor.setText(this.book.getAuthor());
            int pages = this.book.getPages();
            //bookPages.setText("Pages: " + pages);
        }

        list = (ListView) view.findViewById(R.id.bookReviewList);

        Model.getInstance().getPostsByBookAndUserAsync(userId, book.getBookID(), new Model.GetPostsAsyncListener() {
            @Override
            public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray, ArrayList<Book> bookArray) {
                if (userArray == null && bookArray == null) {
                    int last_page = postArray.get(postArray.size()-1).getCurrentPage();
                    data = postArray;
                    BookProgressAdapter adapter = new BookProgressAdapter();
                    list.setAdapter(adapter);
                    bookPages.setText("Page " + last_page + " of " + book.getPages());
                    bookProgress.setMax(book.getPages());
                    bookProgress.setProgress(last_page);
                }
            }
        });

        Button update = (Button) view.findViewById(R.id.updateBookProgressBtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null)
                    delegate.OnUpdateProgress(book);
            }
        });

        Button review = (Button) view.findViewById(R.id.othersReviewBtn);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delegate != null)
                    delegate.OnOthersReview(book);
            }
        });

        return view;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBook(Book book) { this.book = book;}

    class BookProgressAdapter extends BaseAdapter {

        public BookProgressAdapter() {
        }

        @Override
        public int getCount() { //returns the size of the list
            return data.size();
        }

        @Override
        public Object getItem(int position) { //returns the post
            return data.get(position);
        }

        @Override
        public long getItemId(int position) { //returns post id
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                convertView = inflater.inflate(R.layout.book_progress_single_row, null);
            }

            TextView pages = (TextView) convertView.findViewById(R.id.atPageInfo);
            TextView review = (TextView) convertView.findViewById(R.id.myReviewAtPage);

            Post post = data.get(position);
            String title = "Page "+post.getCurrentPage() + ": ";

            review.setText(post.getText());
            if (post.isFinished())
            {
                title += " - Marked as finished!";
            }
            pages.setText(title);




            return convertView;
        }
    }
}
