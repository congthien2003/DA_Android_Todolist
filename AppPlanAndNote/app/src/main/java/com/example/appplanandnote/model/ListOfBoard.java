package com.example.appplanandnote.model;

import com.google.gson.annotations.SerializedName;

public class ListOfBoard {
    @SerializedName("listID")
    private int listID;
    @SerializedName("listName")
    private String listName;
    @SerializedName("process")
    private float process;
    @SerializedName("boardID")
    private int boardID;
    //Hàm khởi tạo
    public ListOfBoard(String listName, float process, int boardID) {

        this.listName = listName;
        this.process = process;
        this.boardID = boardID;
    }
    //Phương thức GET, SET
    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }
}
