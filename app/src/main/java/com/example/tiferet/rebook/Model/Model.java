package com.example.tiferet.rebook.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alon Abadi on 1/5/2016.
 **/

public class Model {
    private final static Model instance = new Model();
    Context context;
    ModelSql modelSql;
    ModelParse model;

    private Model(){
        init(context);
    }

    public static Model getInstance(){
        return instance;
    }

    public void init(Context context){
        model = new ModelParse();
        model.init(context);
        modelSql = new ModelSql(context);
    }

/*    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/

    public void updateReadStatus(final Post post){ model.updateReadStatus(post);}

    public interface GetBooksListener{
        void onBooksArrived(ArrayList<Book> books);
    }
    //Parse
    public void getAllBooks(GetBooksListener listener){
        model.getAllBooks(listener);
    }
    //SQL
    public List<Book>getAllBooks(){
        return modelSql.getAllBooks();
    }

    //Parse + SQL
    public void addPost(Post post){
       /* if(isNetworkAvailable()) {
            model.addPost(post);
        }*/
        model.addPost(post);
        modelSql.addPost(post);
    }

    public void addBook(Book book){
        model.addBook(book);
        modelSql.addBook(book);
    }

    public void addBookToUser(Book book){ model.addBookToUser(book);}

    public Book getBookById(String id){
        return model.getBookById(id);
    }

    public interface GetReadingStatusListener{
        public void onReadingStatusArrived(ArrayList<Book> bookList);
    }

    public void startFollowing(String userId){
        model.startFollowing(userId);
    }
    public void stopFollowing(String userId){
        model.stopFollowing(userId);
    }


    public boolean amIFollowing(String userId){
        return model.amIFollowing(userId);
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

    public interface GetBookReviewsAsyncListener{
        public void onPostsArrived(ArrayList<Post> postArray, ArrayList<User> userArray);
    }

    public void getPostsAsync(String userId, GetPostsAsyncListener listener){
        model.getPostsAsync(userId, listener);
    }

    public void getBookReviewsAsync(String bookId, GetBookReviewsAsyncListener listener){
        model.getBookReviewsAsync(bookId, listener);
    }

    public void getPostsByBookAndUserAsync(String userId, String bookId, GetPostsAsyncListener listener ){
        model.getPostsByBookAndUserAsync(userId, bookId, listener);
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