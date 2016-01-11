package com.example.tiferet.rebook.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public void updateReadStatus(final Post post){ model.updateReadStatus(post);}

    public interface GetBooksListener{
        void onBooksArrived(ArrayList<Book> books);
    }
    public void getAllBooks(GetBooksListener listener){
        model.getAllBooks(listener);
    }

    public void addPost(Post post){ model.addPost(post);}

    public void addBook(Book book){
        model.addBook(book);
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
        model.getBookByIdAsync(id, listener);
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

    public void saveImage(final Bitmap imageBitmap, final String imageName) {
        //saveImageToFile(imageBitmap,imageName); // synchronously save image locally
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                model.saveImage(imageBitmap,imageName);
            }
        });
        d.start();
    }

    public interface LoadImageListener{
        public void onResult(Bitmap imageBmp);
    }

    public void loadImage(final String imageName, final LoadImageListener listener) {
        AsyncTask<String,String,Bitmap> task = new AsyncTask<String, String, Bitmap >() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = loadImageFromFile(imageName);              //first try to fin the image on the device
                if (bmp == null) {                                      //if image not found - try downloading it from parse
                    bmp = model.loadImage(imageName);
                    //if (bmp != null) saveImageToFile(bmp,imageName);    //save the image locally for next time
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                listener.onResult(result);
            }
        };
        task.execute();
    }

    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        FileOutputStream fos;
        OutputStream out = null;
        try {
            File dir = context.getExternalFilesDir(null);
            out = new FileOutputStream(new File(dir,imageFileName));
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromFile(String fileName){
        String str = null;
        Bitmap bitmap = null;
        try {
            File dir = context.getExternalFilesDir(null);
            InputStream inputStream = new FileInputStream(new File(dir,fileName));
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }





}
