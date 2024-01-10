package com.example.appplanandnote.model;

import com.google.gson.annotations.SerializedName;

public class CardOfList {
    @SerializedName("cardID")
    private int cardID;
    @SerializedName("cardName")
    private String cardName;
    @SerializedName("content")
    private String content;
    @SerializedName("cardStatus")
    private int cardStatus;
    @SerializedName("listID")
    private int listID;

    //Hàm khởi tạo
    public CardOfList(){}
    public CardOfList(String cardName, String content, int cardStatus, int listID) {

        this.cardName = cardName;
        this.content = content;
        this.cardStatus = cardStatus;
        this.listID = listID;
    }

    //Phương thức GET, SET

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public int getCardStatus(){return cardStatus;}
    public void setCardStatus(int cardStatus){this.cardStatus = cardStatus;}
}
