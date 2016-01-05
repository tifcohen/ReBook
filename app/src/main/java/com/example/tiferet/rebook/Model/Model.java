package com.example.tiferet.rebook.Model;

import android.content.Context;

import java.util.List;

/**
 * Created by Alon Abadi on 1/5/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    //ModelSql model = new ModelSql();
    ModelParse model = new ModelParse();

    private Model(){}

    public static Model getInstance(){
        return instance;
    }

    public void init(Context context){
        this.context = context;
        model.init(context);
    }

    public List<Book> getAllBooks(){
        return model.getAllBooks();

    }

    public void addBook(Book book){
        model.addBook(book);
    }

    public Book getBookById(String id){
        return model.getBookById(id);
    }

    public List<Post> getAllPosts() {
        return model.getAllPosts();
    }
}
