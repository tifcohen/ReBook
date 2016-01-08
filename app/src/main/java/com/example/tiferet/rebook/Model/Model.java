package com.example.tiferet.rebook.Model;

import android.content.Context;

import java.util.ArrayList;
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

    public interface GetBooksListener{
        void onBooksArrived(ArrayList<Book> books);
    }
    public void getAllBooks(GetBooksListener listener){
        model.getAllBooks(listener);
    }

    public void addBook(Book book){
        model.addBook(book);
    }

    public Book getBookById(String id){
        return model.getBookById(id);
    }

    public interface GetReadingStatusListener{
        public void onReadingStatusArrived(ArrayList<Book> bookList);
    }

    public void getReadingStatusAsync(String id, boolean finished, GetReadingStatusListener listener){
        model.getReadingStatusAsync(id, finished, listener);
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

    public void getPostsByBookAndUserAsync(String userId, String bookId, GetPostsAsyncListener listener ){
        model.getPostsByBookAndUserAsync(userId,bookId,listener);
    }


    public List<Post> getAllPosts() {
        return model.getAllPosts2();
    }

    public void getBookByIdAsync(String id, GetBookListener listener){
        model.getBookByIdAsync(id,listener);
    }

    public void getUserByIdAsync(String id, GetUserListener listener){
        model.getUserByIdAsync(id, listener);
    }


    public interface getMyProfileReadingListListener{
        public void onReadingListArrived();
    }

    public void getBookReadingStatusByUserId(String userId, Boolean finished, getMyProfileReadingListListener listener){
        //model.getBookReadingStatusByUserId(userId,finished,listener);
    }

    public interface GetFollowersListener{
        public void onFollowersArrived(ArrayList<User> followers);
    }

    public void getFollowersList(String id, GetFollowersListener listener) {
        model.getFollowersListByIdAsync(id, listener);
    }

    public interface GetFollowingListener{
        public void onFollowingListArrived(ArrayList<User> followers);
    }

    public void getFollowingListByIdAsync(String id, GetFollowingListener listener) {
        model.getFollowingListByIdAsync(id, listener);
    }





}
