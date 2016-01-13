package com.example.tiferet.rebook.Model;

import com.example.tiferet.rebook.R;
import com.parse.ParseObject;

/**
 * Created by TIFERET on 31-Dec-15.
 */
public class Book {
    String BookID;
    String bookName;
    String author;
    int pages;
    String picture;


    public Book(String bookID, String bookName, String author, int pages, String picture) {
        BookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.pages = pages;
        this.picture = picture;
    }

    public Book(ParseObject p)
    {
        this.BookID = p.getObjectId();
        this.bookName = p.getString("bookName");
        this.author = p.getString("author");
        this.pages = p.getInt("Pages");
        this.picture = p.getString("imageName");
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getStars(int grade){
        final Integer[] starsArray = new Integer[]{
                R.drawable.stars_0,
                R.drawable.stars_1,
                R.drawable.stars_2,
                R.drawable.stars_3,
                R.drawable.stars_4,
                R.drawable.stars_5,
                R.drawable.stars_6,
                R.drawable.stars_7,
                R.drawable.stars_8,
                R.drawable.stars_9,
                R.drawable.stars_10};
        if (grade > 0 && grade < 11)
            return starsArray[grade];
        else
            return starsArray[0];
    }
}
