package com.example.tiferet.rebook.Model;

/**
 * Created by TIFERET on 31-Dec-15.
 */
public class User {
    String userId;
    String email;
    String fName;
    String lName;
    String profPicture;
    String birthDate;

    public User(String userId, String email, String fName, String lName, String profPicture, String birthDate) {
        this.userId = userId;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.profPicture = profPicture;
        this.birthDate = birthDate;
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
