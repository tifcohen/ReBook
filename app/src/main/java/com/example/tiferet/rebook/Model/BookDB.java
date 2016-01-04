package com.example.tiferet.rebook.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by TIFERET on 04-Jan-16.
 */
public class BookDB {
    private final static BookDB instance = new BookDB();
    List<Book> db = new LinkedList<Book>();

    private BookDB() {
        init();
    }

    public static BookDB getInstance() {
        return instance;
    }

    private void init(){
        for (int i = 0; i < 10; i++) {
            Book b = new Book("" + i, "" + i, "" + i, i, "" + i);
            addBook(b);
        }
    }

    public void addBook(Book book){
        db.add(book);
    }

    public List<Book> getAllBooks(){
        return db;
    }
}

