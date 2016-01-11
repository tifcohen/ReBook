package com.example.tiferet.rebook.Model.SqlObjects;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.ModelSql;
import com.example.tiferet.rebook.Model.Post;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by TIFERET on 10-Jan-16.
 */
public class PostSql {
    private static final String TABLE = "POSTS";
    private static final String ID = "ID";
    private static final String BOOK = "BOOK";
    private static final String USER = "USER";
    private static final String PAGE = "PAGE";
    private static final String POST = "POST";
    private static final String GRADE = "GRADE";
    private static final String FINISH = "FINISH";
    private static final String DATE = "DATE";


    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " TEXT PRIMARY KEY," + BOOK + " TEXT," + USER + " TEXT," + PAGE + " INT," +
                POST + " TEXT," + GRADE + " INT," + FINISH + " INT," + DATE + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE);
    }

    public static void addPost(ModelSql.MyOpenHelper dbHelper, Post post) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, post.getPostID());
        values.put(BOOK, post.getBookID());
        values.put(USER, post.getUserID());
        values.put(PAGE, post.getCurrentPage());
        values.put(POST, post.getText());
        values.put(GRADE, post.getGrade());
        if(post.isFinished()){
            values.put(FINISH, 1);
        }
        else {
            values.put(FINISH, 0);
        }
        DateFormat df = DateFormat.getDateInstance();
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String time = df.format(new Date());
        Log.d("TAG", time);
        values.put(DATE, time);

        db.insert(TABLE, ID, values);
    }
}
