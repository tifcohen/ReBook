package com.example.tiferet.rebook.Model;

import android.content.Context;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
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
        query.orderByDescending("createdAt");

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
    public List<Post> getAllPosts2(){
        List<Post> allPosts = new LinkedList<Post>();
        ParseQuery query = new ParseQuery("Post");
        query.orderByDescending("createdAt");
        query.include("book");
        query.include("user");

        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data) {
                String userID = po.getString("userID");
                ParseObject book = po.getParseObject("book");
                Log.d("hello","Author: " + book.getString("author"));
                String bookID = po.getString("book");
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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    User user = new User(object);
                    listener.onUserArrived(user);
                } else {
                    Log.d("Debug", "User was not found " + id);
                }
            }
        });
    }


    public void getPostsAsync(String userId, Model.GetPostsAsyncListener listener) {
        ArrayList<Post> postArray = new ArrayList<Post>();
        ArrayList<Book> bookArray = new ArrayList<Book>();
        ArrayList<User> userArray = new ArrayList<User>();

        ParseQuery query = new ParseQuery("Post");
        query.orderByAscending("createdAt");
        query.include("book");
        query.include("user");
        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data){

                User user = new User(po.getParseObject("user"));
                Book book = new Book(po.getParseObject("book"));
                Post post = new Post(po,user.getUserId(),book.getBookID());
                postArray.add(post);
                bookArray.add(book);
                userArray.add(user);
            }
            listener.onPostsArrived(postArray, userArray, bookArray);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    public void getFollowersListByIdAsync(final String id, final Model.GetFollowersListener listener) {


        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("_User");
        query1.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    ArrayList<User> usersArray = new ArrayList<User>();
                    ParseQuery query = new ParseQuery("Follow");
                    query.whereEqualTo("to", object);
                    query.include("from");
                    try{
                        List<ParseObject> data = query.find();
                        for (ParseObject po : data){
                            User user = new User(po.getParseObject("from"));
                            usersArray.add(user);
                        }
                        listener.onFollowersArrived(usersArray);
                    } catch (com.parse.ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.d("Debug", "User was not found " + id);
                }
            }
        });

        ArrayList<User> usersArray = new ArrayList<User>();
        ParseQuery query = new ParseQuery("Follow");
        query.whereEqualTo("to", id);
        query.include("from");
        try{
            List<ParseObject> data = query.find();
            for (ParseObject po : data){
                User user = new User(po.getParseObject("from"));
                usersArray.add(user);
            }
            listener.onFollowersArrived(usersArray);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

    }
}
