package com.example.tiferet.rebook.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by TIFERET on 04-Jan-16.
 */
public class UserDB {
    private final static UserDB instance = new UserDB();
    List<User> db = new LinkedList<User>();

    private UserDB() {
        init();
    }

    public static UserDB getInstance() {
        return instance;
    }

    private void init(){
        for (int i = 0; i < 10; i++) {
            User u = new User(i+"", "" + i, "" + i, "" + i, "" + i, "" + i, "");
            addUser(u);
        }
    }

    public void addUser(User user){
        db.add(user);
    }

    public List<User> getAllUsers(){
        return db;
    }

}
