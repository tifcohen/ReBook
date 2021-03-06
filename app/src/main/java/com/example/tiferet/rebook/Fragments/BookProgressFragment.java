package com.example.tiferet.rebook.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tiferet.rebook.MainActivity;
import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.Model;
import com.example.tiferet.rebook.Model.Post;
import com.example.tiferet.rebook.Model.User;
import com.example.tiferet.rebook.R;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by TIFERET on 05-Jan-16.
 */
public class BookProgressFragment extends Fragment {

    public interface BookProgressFragmentDelegate{
        void OnUpdateProgress(Book book);
        void OnOthersReview(Book book);
        void onLogout();
        void OnBookProgress(String userId, Book book);
        void onNewsFeed();
    }

    ListView list;
    ArrayList<Post> data;
    ArrayList<User> users;
    Book book;
    String bookId;

    public User getCurr() {
        return curr;
    }

    public void setCurr(User curr) {
        this.curr = curr;
    }

    User curr;// = new User(ParseUser.getCurrentUser());
    //User curr = null;
    String userId;
    TextView bookProgressPages;
    ProgressBar bookProgress;
    RelativeLayout bookProgressLayout;
    TextView bookProfileTitle;
    ImageView bookImage;

    BookProgressFragmentDelegate delegate;
    public void setDelegate(BookProgressFragmentDelegate delegate){ this.delegate = delegate;}

    public BookProgressFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_progress_fragment, container, false);
        final TextView bookName = (TextView) view.findViewById(R.id.bookProgressName);
        bookImage = (ImageView) view.findViewById(R.id.bookProgressImage);
        final TextView bookAuthor = (TextView) view.findViewById(R.id.bookProgressAuthor);
        bookProgress = (ProgressBar) view.findViewById(R.id.progressBarBook);
        bookProgressPages = (TextView) view.findViewById(R.id.bookProgressPages);

        if (book == null) {
            Model.getInstance().getBookByIdAsync(bookId, new Model.GetBookListener() {
                @Override
                public void onBookArrived(Book book) {
                    setBook(book);
                    bookName.setText(book.getBookName());
                    bookAuthor.setText(" By " + book.getAuthor() + ". Pages: " + book.getPages());
                    int pages = book.getPages();
                    //bookPages.setText("Pages: " + pages);
                    if (book.getPicture() != null) {
                        if (!book.getPicture().equals("")) {
                            Model.getInstance().loadImage(book.getPicture(), new Model.LoadImageListener() {
                                @Override
                                public void onResult(Bitmap imageBmp) {
                                    if (imageBmp != null) {
                                        bookImage.setImageBitmap(imageBmp);
                                    }
                                }
                            });
                        }
                        else {
                            bookImage.setImageResource(R.drawable.default_book);
                        }
                    }
                    else {
                        bookImage.setImageResource(R.drawable.default_book);
                    }
                }
            });
        }
        else if(book != null){
            bookName.setText(book.getBookName());
            bookAuthor.setText(" By " + book.getAuthor() + ". Pages: " + book.getPages());
            int pages = book.getPages();
            //bookPages.setText("Pages: " + pages);
            if (book.getPicture() != null) {
                if (!book.getPicture().equals("")) {
                    Model.getInstance().loadImage(book.getPicture(), new Model.LoadImageListener() {
                        @Override
                        public void onResult(Bitmap imageBmp) {
                            if (imageBmp != null) {
                                bookImage.setImageBitmap(imageBmp);
                            }
                        }
                    });
                }
                else {
                    bookImage.setImageResource(R.drawable.default_book);
                }
            }
            else {
                bookImage.setImageResource(R.drawable.default_book);
            }
        }

        list = (ListView) view.findViewById(R.id.bookReviewList);
        Button update = (Button) view.findViewById(R.id.updateBookProgressBtn);
        Button more2 = (Button) view.findViewById(R.id.othersReviewBtn);
        bookProfileTitle = (TextView) view.findViewById(R.id.bookProfileTitle);

        if (curr != null) {
            more2.setVisibility(View.GONE);
            more2.setText("More");

            more2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Debug", "THIS SHOULD HAPPEN!!! ");
                    delegate.OnBookProgress("",book);
                }
            });

            Model.getInstance().getPostsByBookAndUserAsync(userId, book.getBookID(), new Model.GetPostsAsyncListener() {
                @Override
                public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray, ArrayList<Book> bookArray) {
                    if (userArray == null && bookArray == null) {

                        int last_page = postArray.get(postArray.size() - 1).getCurrentPage();
                        data = postArray;
                        BookProgressAdapter adapter = new BookProgressAdapter();
                        list.setAdapter(adapter);
                        bookProfileTitle.setText("My Progress (Page "+last_page+"/"+book.getPages()+"):");
                        bookProgress.setMax(book.getPages());
                        bookProgress.setProgress(last_page);
                    }
                }
            });
        }
        else
        {
            more2.setVisibility(View.GONE);

            update.setVisibility(View.GONE);
            bookProgress = (ProgressBar) view.findViewById(R.id.progressBarBook);
            bookProgressLayout = (RelativeLayout) view.findViewById(R.id.bookProgBar);
            bookProgress.setVisibility(View.GONE);
            bookProgressLayout.setVisibility(View.GONE);
            Model.getInstance().getBookByIdAsync(bookId, new Model.GetBookListener() {
                @Override
                public void onBookArrived(Book book) {
                    Model.getInstance().getBookReviewsAsync(book.getBookID(), new Model.GetBookReviewsAsyncListener() {
                        @Override
                        public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray) {
                            data = postArray;
                            users = userArray;
                            BookWorldProgressAdapter adapter = new BookWorldProgressAdapter();
                            list.setAdapter(adapter);
                            if (postArray.size() == 0) {
                                bookProfileTitle.setText("There are no current reviews.");
                            } else {
                                bookProfileTitle.setText(postArray.size() + " Reviews of this book:");
                            }
                        }
                    });
                }
            });


        }


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

    public void setBookId(String bookId) {
        this.bookId = bookId;
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

            final ImageView stars = (ImageView) convertView.findViewById(R.id.stars);
            final TextView pages = (TextView) convertView.findViewById(R.id.pageTextView);
            final TextView action = (TextView) convertView.findViewById(R.id.actionTextView);
            final TextView review = (TextView) convertView.findViewById(R.id.bookReview);

            if (curr != null)
            {
                //Single user progressbar
                Post post = data.get(position);
                String title = "";
                if (post.getCurrentPage() > 0){
                    pages.setVisibility(View.VISIBLE);
                    pages.setText("Page " + post.getCurrentPage() + ": ");

                    if (post.isFinished())
                    {
                        action.setVisibility(View.VISIBLE);
                        action.setText(title);
                        title = "Finished Reading! ";
                    }
                    else
                    {
                        action.setVisibility(View.GONE);
                    }

                }
                else
                {
                    pages.setVisibility(View.GONE);
                    title = "Started Reading";
                    action.setText(title);
                    action.setVisibility(View.VISIBLE);
                }
                review.setText(post.getText());
                stars.setImageResource(book.getStars(post.getGrade()));
                stars.setVisibility(View.VISIBLE);
                if (post.getText().equals("") && post.getGrade() == 0)
                    stars.setVisibility(View.GONE);
            }
            return convertView;
        }
    }







    class BookWorldProgressAdapter extends BaseAdapter {

        public BookWorldProgressAdapter() {
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
                convertView = inflater.inflate(R.layout.news_feed_single_row, null);
            }

            final ImageView stars = (ImageView) convertView.findViewById(R.id.stars);
            final TextView pages = (TextView) convertView.findViewById(R.id.pageTextView);
            final TextView action = (TextView) convertView.findViewById(R.id.actionTextView);
            final TextView review = (TextView) convertView.findViewById(R.id.bookReview);

            final TextView bookName = (TextView) convertView.findViewById(R.id.bookName);
            final TextView bookReview = (TextView) convertView.findViewById(R.id.bookReview);
            final TextView userName = (TextView) convertView.findViewById(R.id.userProfileName);
            final TextView page = (TextView) convertView.findViewById(R.id.pageTextView);
            //action = (TextView) convertView.findViewById(R.id.actionTextView);
            final TextView action2 = (TextView) convertView.findViewById(R.id.action2TextView);



            Post post = data.get(position);
            User user = users.get(position);
            userName.setText(user.getfName() + " " + user.getlName());
            userName.setTag(user);

            if (book.getBookName().length() > 27) {
                bookName.setText(book.getBookName().substring(0, 27) + "...");
            }
            else {
                bookName.setText(book.getBookName());
            }
            bookName.setTag(post);


            if (post.getCurrentPage() == 0 && post.getGrade() == 0) {
                action.setText("Started ");
                bookReview.setVisibility(View.GONE);
                action2.setText("Not yet rated.");
                page.setVisibility(View.GONE);
                stars.setVisibility(View.GONE);
                action2.setVisibility(View.GONE);

            }
            else
            {
                page.setVisibility(View.VISIBLE);
                page.setText(" Page: " + post.getCurrentPage());
                stars.setImageResource(book.getStars(post.getGrade()));
                stars.setVisibility(View.VISIBLE);
                action2.setVisibility(View.VISIBLE);
                action2.setText("Rated ");
                if (post.isFinished())
                    action.setText("finished ");
                else
                    action.setText("is reading ");

                if (post.getText().isEmpty())
                {
                    bookReview.setVisibility(View.GONE);
                }
                else
                {
                    bookReview.setText(post.getText());
                    bookReview.setVisibility(View.VISIBLE);
                }
            }




            return convertView;
        }
    }
}
