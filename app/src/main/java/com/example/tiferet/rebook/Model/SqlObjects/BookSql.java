package com.example.tiferet.rebook.Model.SqlObjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode;

import com.example.tiferet.rebook.Model.Book;
import com.example.tiferet.rebook.Model.ModelSql;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by TIFERET on 10-Jan-16.
 */
public class BookSql {
    private static final String TABLE = "Books";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String PAGES = "pages";
    private static final String IMAGE_NAME = "imageName";

    public static void create(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + ID + " TEXT PRIMARY KEY," + NAME + " TEXT," + AUTHOR + " TEXT, " +
                PAGES + " INTEGER," + IMAGE_NAME + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE);
    }

    public static void addBook(ModelSql.MyOpenHelper dbHelper, Book bk) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, bk.getBookID());
        values.put(NAME, bk.getBookName());
        values.put(AUTHOR, bk.getAuthor());
        values.put(PAGES, bk.getPages());
        values.put(IMAGE_NAME, bk.getPicture());

        db.insert(TABLE, ID, values);
    }

    public static int getCount(ModelSql.MyOpenHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor mCount= db.rawQuery("select count(*) from " + TABLE, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;


    }
    public static List<Book> getAllBooks(ModelSql.MyOpenHelper dbHelper) {
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
    }
}
