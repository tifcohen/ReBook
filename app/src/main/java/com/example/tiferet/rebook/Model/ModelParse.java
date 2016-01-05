package com.example.tiferet.rebook.Model;

import android.content.Context;

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
        //Parse.initialize(context, "ITNj6efrX3wAQY6atirz73j41dfRvuu3HKQt2NUB", "CCkY6qHc942bc1gIsWctt28Li76NzFbmTMcSBNAn");

        /*
            String postID;
            String userID;
            String bookID;
            String text;
            Date date;
            int currentPage;
            boolean finished;
            int grade;
        */
        /*

        for (int i = 0; i < 3; i++) {
            ParseObject testObject = new ParseObject("Posts");
            testObject.put("userID", "" + (i * 145 % 100));
            testObject.put("bookID", "" + (i * 157 % 100));
            testObject.put("text", "This is my post's content");
            testObject.put("date", "2016");
            testObject.put("currentPage", 1);
            testObject.put("finished", false);
            testObject.put("grade", 10);
            testObject.saveInBackground();
        }*/

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
        for(ParseObject po : data){
            String bookName = po.getString("bookName");
            String author = po.getString("author");
            int pages = po.getInt("Pages");
            String imageName = po.getString("imageName");

        }
        return null;
    }
}
