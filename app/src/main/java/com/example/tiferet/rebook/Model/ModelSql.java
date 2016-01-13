package com.example.tiferet.rebook.Model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.text.TextUtils;

import com.example.tiferet.rebook.Model.SqlObjects.BookSql;
import com.example.tiferet.rebook.Model.SqlObjects.PostSql;
import com.example.tiferet.rebook.Model.SqlObjects.SyncSql;
import com.example.tiferet.rebook.Model.SqlObjects.UserSql;

import java.util.List;

/**
 * Created by TIFERET on 31-Dec-15.
 */
public class ModelSql{
    private MyOpenHelper dbHelper;
    public ModelSql() {
    }
    //book table, readStatus table, post table, user table

    //add book
    //book progress --> readStatus + Post
    //list book in progress
    //list book shelf
    //post table - shoes only current user posts
    //newsfeed gets posts from local post table

    public void init(Context context){
        if (dbHelper == null)
        {
            dbHelper = new MyOpenHelper(context);
        }
    }
    public void addBook(Book book) {
        BookSql.addBook(dbHelper, book);
    }

    public int getCountBooks(){
        return BookSql.getCount(dbHelper);
    }

    public List<Book> getAllBooks() {
        return BookSql.getAllBooks(dbHelper);
    }

    public void updateLastUpdated(String date){
        if (!TextUtils.isEmpty(date))
            SyncSql.add(dbHelper, date);
    }
    public void addPost(Post post) {
        PostSql.addPost(dbHelper, post);
    }

    public String getLastUpdated(){
        return SyncSql.getLastUpdated(dbHelper);
    }

    public class MyOpenHelper extends SQLiteOpenHelper {
        final static String dbName = "database.db";
        final static int version = 3;

        public MyOpenHelper(Context context) {
            super(context, dbName, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            BookSql.create(db);
          //  UserSql.create(db);
            PostSql.create(db);
            SyncSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //BookSql.drop(db);
            PostSql.drop(db);
            SyncSql.drop(db);
            onCreate(db);
        }
    }
}
