package com.example.tiferet.rebook.Model;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alon Abadi on 1/5/2016.
 */
public class ModelParse {

    public void init(Context context) {

    }

    public void getAllBooks(Model.GetBooksListener listener) {
        ArrayList<Book> allBooks = new ArrayList<>();
        ParseQuery query = new ParseQuery("Books");
        query.orderByAscending("bookName");
        try {
            List<ParseObject> data = query.find();
            for(ParseObject po : data){
                Book book = new Book(po);
                allBooks.add(book);
            }
            listener.onBooksArrived(allBooks);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public List<Post> getAllPosts(){
        List<Post> allPosts = new LinkedList<Post>();
        ParseQuery query = new ParseQuery("Posts");
        query.orderByDescending("createdAt");

        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data) {
                String userID = po.getString("userID");
                String bookID = po.getString("bookID");
                String text = po.getString("text");
                Date date = po.getDate("createdAt");
                int currentPage = po.getInt("currentPage");
                boolean finished = po.getBoolean("finished");
                int grade = po.getInt("grade");
                Post temp = new Post("1", userID, bookID, text, date, currentPage, finished, grade);
                allPosts.add(temp);
            }
            return allPosts;

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }



        return null;
    }
    public List<Post> getAllPosts2(){
        List<Post> allPosts = new LinkedList<Post>();
        ParseQuery query = new ParseQuery("Post");
        query.orderByDescending("createdAt");
        query.include("book");
        query.include("user");

        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data) {
                String userID = po.getString("userID");
                ParseObject book = po.getParseObject("book");
                String bookID = po.getString("book");
                String text = po.getString("text");
                Date date = po.getDate("createdAt");
                int currentPage = po.getInt("currentPage");
                boolean finished = po.getBoolean("finished");
                int grade = po.getInt("grade");
                Post temp = new Post("1", userID, bookID, text, date, currentPage, finished, grade);
                allPosts.add(temp);
            }
            return allPosts;

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }



        return null;
    }


    public void addBook(final Book book) {
        final ParseObject newBook = new ParseObject("Books");
        newBook.put("bookName", book.getBookName());
        newBook.put("author", book.getAuthor());
        newBook.put("Pages", book.getPages());
        newBook.put("imageName", book.getPicture());
        newBook.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    book.setBookID(newBook.getObjectId());
                    addBookToUser(book);
                }
                else
                {
                    Log.d("Debug", "Book was not added.");
                }
            }
        });
    }

    public void addBookToUser(Book book){
        final ParseObject newReadStatus = new ParseObject("ReadStatus");
        final ParseUser user = ParseUser.getCurrentUser();
        //final ParseObject user2 = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(book.getBookID(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject newBook, ParseException e) {
                ParseObject newPost = new ParseObject("Post");
                newPost.put("book", newBook);
                newPost.put("user", user);
                newPost.put("finished", false);
                newPost.put("currentPage", 0);
                newPost.put("grade", 0);
                newPost.put("text", "");
                newPost.saveInBackground();
                newReadStatus.put("book", newBook);
                newReadStatus.put("user", user);
                newReadStatus.put("finished", false);
                newReadStatus.put("currentPage", 0);
                newReadStatus.saveInBackground();
            }
        });
    }


    public Book getBookById(String id) {
        //ParseObject query = new ParseObject("Books");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.whereEqualTo("objectId", id);
        try {
            query.find();

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void getBookByIdAsync(final String id, final Model.GetBookListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    Book book = new Book(object.getObjectId(), object.getString("bookName"), object.getString("author"), object.getInt("Pages"), object.getString("imageName"));
                    listener.onBookArrived(book);
                    Log.d("Debug", "found " + book.bookName + "");
                } else {
                    Log.d("Debug", "Book was not found" + id);
                }
            }
        });
    }

    public void getUserByIdAsync(final String id, final Model.GetUserListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    User user = new User(object);
                    listener.onUserArrived(user);
                } else {
                    Log.d("Debug", "User was not found " + id);
                }
            }
        });
    }


    public void getPostsAsync(String userId, Model.GetPostsAsyncListener listener) {
        ArrayList<Post> postArray = new ArrayList<Post>();
        ArrayList<Book> bookArray = new ArrayList<Book>();
        ArrayList<User> userArray = new ArrayList<User>();

        ParseQuery query = new ParseQuery("Post");
        query.orderByDescending("createdAt");
        query.include("book");
        query.include("user");
        try {
            List<ParseObject> data = query.find();
            for (ParseObject po : data){

                User user = new User(po.getParseObject("user"));
                Book book = new Book(po.getParseObject("book"));
                Post post = new Post(po,user.getUserId(),book.getBookID());
                postArray.add(post);
                bookArray.add(book);
                userArray.add(user);
            }
            listener.onPostsArrived(postArray, userArray, bookArray);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
    }

    public void getFollowersListByIdAsync(final String id, final Model.GetFollowersListener listener) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("_User");
        query1.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    ArrayList<User> usersArray = new ArrayList<User>();
                    ParseQuery query = new ParseQuery("Follow");
                    query.whereEqualTo("to", object);
                    query.include("from");
                    try {
                        List<ParseObject> data = query.find();
                        for (ParseObject po : data) {
                            User user = new User(po.getParseObject("from"));
                            usersArray.add(user);
                        }
                        listener.onFollowersArrived(usersArray);
                    } catch (com.parse.ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.d("Debug", "User was not found " + id);
                }
            }
        });
    }

    public void getFollowingListByIdAsync(final String id, final Model.GetFollowingListener listener) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("_User");
        query1.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    ArrayList<User> usersArray = new ArrayList<User>();
                    ParseQuery query = new ParseQuery("Follow");
                    query.whereEqualTo("from", object);
                    query.include("to");
                    try {
                        List<ParseObject> data = query.find();
                        for (ParseObject po : data) {
                            User user = new User(po.getParseObject("to"));
                            usersArray.add(user);
                        }
                        listener.onFollowingListArrived(usersArray);
                    } catch (com.parse.ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    Log.d("Debug", "User was not found " + id);
                }
            }
        });
    }

    public void getReadingStatusAsync(String id, final boolean finished, final Model.GetReadingStatusListener listener) {
        final ArrayList<Book> bookList = new ArrayList<>();
        final ParseQuery<ParseObject> query1 = new ParseQuery("_User");
        query1.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseQuery query2 = new ParseQuery("ReadStatus");
                    query2.whereEqualTo("finished", finished);
                    query2.whereEqualTo("user", object);
                    query2.include("book");
                    try {
                        List<ParseObject> data = query2.find();
                        for (ParseObject po : data) {
                            Book book = new Book(po.getParseObject("book"));
                            bookList.add(book);
                        }
                        listener.onReadingStatusArrived(bookList);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void getPostsByBookAndUserAsync(final String userId, final String bookId, final Model.GetPostsAsyncListener listener) {
        final ArrayList<Post> postArray = new ArrayList<Post>();
        final ParseQuery<ParseObject> query1 = new ParseQuery("_User");
        query1.getInBackground(userId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject user, ParseException e) {
                if (e == null){
                    final ParseQuery<ParseObject> query2 = new ParseQuery("Books");
                    query2.getInBackground(bookId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject book, ParseException e2) {
                            if (e2 == null) {
                                ParseQuery query = new ParseQuery("Post");
                                query.orderByAscending("createdAt");
                                query.whereEqualTo("book", book);
                                query.whereEqualTo("user", user);
                                try {
                                    List<ParseObject> data = query.find();
                                    for (ParseObject po : data) {
                                        Post post = new Post(po, userId, bookId);
                                        postArray.add(post);
                                    }
                                    listener.onPostsArrived(postArray, null, null);

                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                            } else {
                                Log.d("Debug", "Book was not found. " + bookId);
                            }

                        }
                    });
                }
                else
                {
                    Log.d("Debug", "User was not found. " + userId);
                }
            }
        });
    }

    public void startFollowing(final String userId) {
        if (amIFollowing(userId) == false) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.getInBackground(userId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject to, com.parse.ParseException e) {
                    if (e == null) {
                        ParseObject follow = new ParseObject("Follow");
                        follow.put("to", to);
                        follow.put("from", ParseUser.getCurrentUser());
                        try {
                            follow.save();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        Log.d("Debug", "User was not found " + userId);
                    }
                }
            });
        }
    }



    public boolean amIFollowing(String userId)
    {
        try
        {
            ParseObject to = ParseQuery.getQuery("_User").get(userId);
            ParseObject from = ParseUser.getCurrentUser();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
            query.whereEqualTo("from",from);
            query.whereEqualTo("to", to);
            ParseObject p = query.getFirst();
            if (p == null)
                return false;
            else
                return true;
        } catch (ParseException e) {
            Log.d("debug","prepare!");
            e.printStackTrace();
            return false;
        }
    }

    public void stopFollowing(final String userId) {
        if (amIFollowing(userId) == true)
        {
            try
            {
                ParseObject to = ParseQuery.getQuery("_User").get(userId);
                ParseObject from = ParseUser.getCurrentUser();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
                query.whereEqualTo("from",from);
                query.whereEqualTo("to", to);
                ParseObject p = query.getFirst();
                if (p != null)
                    p.deleteInBackground();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void addPost(final Post post) {
        final ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(post.getBookID(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject newBook, ParseException e) {
                ParseObject newPost = new ParseObject("Post");
                newPost.put("book", newBook);
                newPost.put("user", user);
                newPost.put("finished", post.isFinished());
                newPost.put("currentPage", post.getCurrentPage());
                newPost.put("grade", post.getGrade());
                newPost.put("text", post.getText());
                newPost.saveInBackground();
            }
        });
    }

    public void updateReadStatus(final Post post){ // updates the read status table
        final ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(post.getBookID(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject book, ParseException e) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("ReadStatus");
                query.whereEqualTo("book", book);
                query.whereEqualTo("user", user);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        for (ParseObject po : objects) {
                            po.put("currentPage", post.getCurrentPage());
                            po.put("finished", post.isFinished());
                            po.saveInBackground();
                        }
                    }
                });
            }
        });
    }

    public void getBookReviewsAsync(String bookId, final Model.GetBookReviewsAsyncListener listener) {
        final ArrayList<Post> postArray = new ArrayList<Post>();
        final ArrayList<User> userArray = new ArrayList<User>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Books");
        query.getInBackground(bookId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject newBook, ParseException e) {
                Book book = new Book(newBook);
                ParseQuery query = new ParseQuery("Post");
                query.include("user");
                query.include("book");
                query.whereEqualTo("book", newBook);
                query.orderByDescending("createdAt");
                query.whereEqualTo("finished", true);

                try {
                    List<ParseObject> data = query.find();
                    for (ParseObject po : data) {

                        User user = new User(po.getParseObject("user"));
                        Post post = new Post(po, user.getUserId(), book.getBookID());
                        postArray.add(post);
                        userArray.add(user);
                    }
                    listener.onPostsArrived(postArray,userArray);
                }catch(ParseException e1){
                    e1.printStackTrace();
                }
            }
        });
    }


    public Bitmap loadImage(String imageName) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("images");
        query.whereEqualTo("name", imageName);
        try {
            List<ParseObject> list = query.find();
            if (list.size() > 0) {
                ParseObject po = list.get(0);
                ParseFile pf = po.getParseFile("image");
                byte[] data = pf.getData();
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                return bmp;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImage(Bitmap imageBitmap, String imageName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        ParseFile file = new ParseFile(imageName, byteArray);
        try {
            file.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ParseObject images = new ParseObject("images");
        images.put("name", imageName);
        images.put("image", file);
        try {
            images.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}