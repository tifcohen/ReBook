package com.example.tiferet.rebook.Model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alon Abadi on 12/31/2015.
 */
public class PostDB {
    private final static PostDB instance = new PostDB();
    List<Post> db = new LinkedList<Post>();

    private PostDB() {
        init();
    }

    public static PostDB getInstance() {
        return instance;
    }

    private void init(){
        for (int i = 0; i < 20; i++) {
            Post p = new Post("" + i, "" + i, "Text", new Date(), i, false, 10);
            addPost(p);
        }
    }

    public void addPost(Post post){
        db.add(post);
    }

    public List<Post> getAllPosts(){
        return db;
    }
}
