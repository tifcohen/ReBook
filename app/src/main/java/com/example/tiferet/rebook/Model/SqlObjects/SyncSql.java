package com.example.tiferet.rebook.Model.SqlObjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.ModelSql;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alon Abadi on 1/13/2016.
 */
public class SyncSql {
    private static final String TABLE = "Sync";
    private static final String ID = "_id";
    private static final String DATE = "date";

    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DATE + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE);
    }

    public static void add(ModelSql.MyOpenHelper dbHelper, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(ID, bk.getBookID());
        values.put(DATE, date);

        db.insertOrThrow(TABLE, null, values);
        //db.insert(TABLE, ID, values);
    }

    public static String getLastUpdated(ModelSql.MyOpenHelper dbHelper)
    {
        String date = "not found";


        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.query(TABLE, null, null, null, null,null, ID + " DESC", "1");

        if (cursor.moveToFirst()) {
            int date_index = cursor.getColumnIndex(DATE);
            date = cursor.getString(date_index);
        }

        return date;
    }
    /* public static List<Book> getAllBooks(ModelSql.MyOpenHelper dbHelper) {
        List<Book> books = new LinkedList<Book>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id_index = cursor.getColumnIndex(ID);
            int name_index = cursor.getColumnIndex(NAME);
            int author_index = cursor.getColumnIndex(AUTHOR);
            int pages_index = cursor.getColumnIndex(PAGES);
            int image_index = cursor.getColumnIndex(IMAGE_NAME);
            do {
                String id = cursor.getString(id_index);
                String name = cursor.getString(name_index);
                String author = cursor.getString(author_index);
                int page = cursor.getInt(pages_index);
                String image = cursor.getString(image_index);

                Book bk = new Book(id, name, author, page, image);
                books.add(bk);
            } while (cursor.moveToNext());
        }

        return books;
    }*/

}
