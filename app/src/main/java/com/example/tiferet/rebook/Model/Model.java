package com.example.tiferet.rebook.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public interface GetBookListener{
        public void onBookArrived(Book book);
    }

    public interface GetUserListener{
        public void onUserArrived(User user);
    }

    public interface GetPostsAsyncListener{
        public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray, ArrayList<Book> bookArray);
    }

    public void getPostsAsync(String userId, GetPostsAsyncListener listener){
        model.getPostsAsync(userId, listener);

    }


    public List<Post> getAllPosts() {
        return model.getAllPosts2();
    }

    public void getBookByIdAsync(String id, GetBookListener listener){
        model.getBookByIdAsync(id,listener);
    }

    public void getUserByIdAsync(String id, GetUserListener listener){
        model.getUserByIdAsync(id,listener);
    }


    public interface getMyProfileReadingListListener{
        public void onReadingListArrived();
    }

    public void getBookReadingStatusByUserId(String userId, Boolean finished, getMyProfileReadingListListener listener){
        //model.getBookReadingStatusByUserId(userId,finished,listener);
    }



}
