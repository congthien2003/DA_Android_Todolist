package com.example.appplanandnote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppUser {
    @SerializedName("userID")
    private int userID;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("sex")
    private String sex;
    @SerializedName("username")
    private String username;
    @SerializedName("userPassword")
    private String userPassword;
    @SerializedName("userStatus")
    private int userStatus;

    //Hàm khởi tạo
    public AppUser(String username, String userPassword, String fullname, String email,
                   String phoneNumber, String sex, int userStatus) {
        this.username = username;
        this.userPassword = userPassword;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.userStatus = userStatus;
    }
    //Phương thức GET, SET
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
