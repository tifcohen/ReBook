package com.example.tiferet.rebook.Model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.example.tiferet.rebook.Model.SqlObjects.BookSql;
import com.example.tiferet.rebook.Model.SqlObjects.PostSql;
import com.example.tiferet.rebook.Model.SqlObjects.UserSql;

import java.util.List;

/**
 * Created by TIFERET on 31-Dec-15.
 */
public class ModelSql{
    private MyOpenHelper dbHelper;
    public ModelSql(Context context) {
        dbHelper = new MyOpenHelper(context);
    }
    //book table, readStatus table, post table, user table

    //add book
    //book progress --> readStatus + Post
    //list book in progress
    //list book shelf
    //post table - shoes only current user posts
    //newsfeed gets posts from local post table

    public void addBook(Book book) {
        BookSql.addBook(dbHelper, book);
    }

    public List<Book> getAllBooks() {
        return BookSql.getAllBooks(dbHelper);
    }

    public void addPost(Post post) {
        PostSql.addPost(dbHelper, post);
    }

    public class MyOpenHelper extends SQLiteOpenHelper {
        final static String dbName = "database.db";
        final static int version = 1;

        public MyOpenHelper(Context context) {
            super(context, dbName, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
         //   BookSql.create(db);
          //  UserSql.create(db);
            PostSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //  BookSql.drop(db);
            PostSql.drop(db);
            onCreate(db);
        }
    }
}
