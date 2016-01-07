package com.example.tiferet.rebook.Model;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alon Abadi on 1/5/2016.
 */
public class ModelParse {

    public void init(Context context) {

    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new LinkedList<Book>();
        return allBooks;
    }

    public List<Post> getAllPosts(){
        List<Post> allPosts = new LinkedList<Post>();
        ParseQuery query = new ParseQuery("Posts");


        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data) {
                String userID = po.getString("userID");
                String bookID = po.getString("bookID");
                String text = po.getString("text");
                Date date = po.getDate("createdAt");
                int currentPage = po.getInt("currentPage");
                boolean finished = po.getBoolean("finished");
                int grade = po.getInt("grade");
                Post temp = new Post("1", userID, bookID, text, date, currentPage, finished, grade);
                allPosts.add(temp);
            }
            return allPosts;

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }



        return null;
    }



    public void addBook(Book book) {

    }


    public Book getBookById(String id) {
        //ParseObject query = new ParseObject("Books");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.whereEqualTo("objectId", id);
        try {
            query.find();

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void getBookByIdAsync(final String id, final Model.GetBookListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    Book book = new Book(object.getObjectId(), object.getString("bookName"), object.getString("author"), object.getInt("Pages"), object.getString("imageName"));
                    listener.onBookArrived(book);
                    Log.d("Debug", "found " + book.bookName + "");
                } else {
                    Log.d("Debug", "Book was not found" + id);
                }
            }
        });
    }

    public void getUserByIdAsync(final String id, final Model.GetUserListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    User user = new User(object.getObjectId(), object.getString("email"), object.getString("fName"), object.getString("lName"), object.getString("profPicture"), object.getString("birthDate"));
                    listener.onUserArrived(user);
                    Log.d("Debug", "found " + user.lName + " " + user.lName);
                } else {
                    Log.d("Debug", "Book was not found" + id);
                }
            }
        });
    }


}
