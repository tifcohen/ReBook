package com.example.tiferet.rebook.Model.SqlObjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.ModelSql;
import com.example.tiferet.rebook.Model.Post;
import com.parse.ParseObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by TIFERET on 10-Jan-16.
 */
public class PostSql {
    private static final String TABLE = "Posts";
    private static final String ID = "id";
    private static final String BOOK = "book";
    private static final String USER = "user";
    private static final String REVIEW = "text";
    private static final String PAGE = "page";
    private static final String GRADE = "grade";
    private static final String FINISH = "finish";
    private static final String DATE = "date";


    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " TEXT PRIMARY KEY," + BOOK + " TEXT," + USER + " TEXT," + REVIEW + " TEXT," + PAGE + " INT," +
                GRADE + " INT," + FINISH + " INT," + DATE + " TEXT);");
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
        values.put(REVIEW, post.getText());
        values.put(PAGE, post.getCurrentPage());
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

    public List<Post> getAllBooks(ModelSql.MyOpenHelper dbHelper){
        List<Post> posts = new LinkedList<Post>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);

        if (cursor.moveToNext()){
            int id_index = cursor.getColumnIndex(ID);
            int book_index = cursor.getColumnIndex(BOOK);
            int user_index = cursor.getColumnIndex(USER);
            int text_index = cursor.getColumnIndex(REVIEW);
            int page_index = cursor.getColumnIndex(PAGE);
            int grade_index = cursor.getColumnIndex(GRADE);
            int finish_index = cursor.getColumnIndex(FINISH);
            int date_index = cursor.getColumnIndex(DATE);

            do {
                String id = cursor.getString(id_index);
                String book = cursor.getString(book_index);
                String text = cursor.getString(text_index);
                String user = cursor.getString(user_index);
                Integer page = cursor.getInt(page_index);
                Integer grade = cursor.getInt(grade_index);
                Boolean finish = (cursor.getInt(finish_index) == 1 )? true : false;
                String date = cursor.getString(finish_index);

                Post post = new Post(id, user, book, text, Calendar.getInstance().getTime(), page, finish, grade);
                posts.add(post);
            } while (cursor.moveToNext());
        }
        return posts;
    }
}
