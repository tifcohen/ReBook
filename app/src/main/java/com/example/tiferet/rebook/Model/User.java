package com.example.tiferet.rebook.Model;

import com.parse.ParseObject;

/**
 * Created by TIFERET on 31-Dec-15.
 */
public class User {
    String userId;
    String username;
    String email;
    String fName;
    String lName;
    String profPicture;
    String birthDate;

    public User(String userId, String username, String email, String fName, String lName, String profPicture, String birthDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.profPicture = profPicture;
        this.birthDate = birthDate;
    }

    public User(ParseObject p){
        this.userId = p.getObjectId();
        this.username = p.getString("username");
        this.email = p.getString("Email");
        this.fName = p.getString("fName");
        this.lName = p.getString("lName");
        this.profPicture = p.getString("imageName");
        this.birthDate = p.getString("birthday");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getProfPicture() {
        return profPicture;
    }

    public void setProfPicture(String profPicture) {
        this.profPicture = profPicture;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
