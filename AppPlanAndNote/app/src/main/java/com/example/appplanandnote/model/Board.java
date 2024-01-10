package com.example.appplanandnote.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Board {
    @SerializedName("boardID")
    private int boardID;
    @SerializedName("boardName")
    private String boardName;
    @SerializedName("boardStatus")
    private int boardStatus;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("userID")
    private int userID;
    //Hàm khởi tạo
    public Board(String boardName){
        this.boardName = boardName;
    }
    public Board(String boardName, int boardStatus, String createdDate, int userID) {

        this.boardName = boardName;
        this.boardStatus = boardStatus;
        this.createdDate = createdDate;
        this.userID = userID;
    }
    //Phương thức GET, SET
    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
