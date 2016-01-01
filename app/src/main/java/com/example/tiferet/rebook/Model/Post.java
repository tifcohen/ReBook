package com.example.tiferet.rebook.Model;

import java.util.Date;

/**
 * Created by Alon Abadi on 12/31/2015.
 */
public class Post {
    String postID;
    String userID;
    String bookID;
    String text;
    Date date;
    int currentPage;
    boolean finished;
    int grade;

    public Post(String postID, String userID, String bookID, String text, Date date, int currentPage, boolean finished, int grade) {
        this.postID = postID;
        this.userID = userID;
        this.bookID = bookID;
        this.text = text;
        this.date = date;
        this.currentPage = currentPage;
        this.finished = finished;
        this.grade = grade;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
