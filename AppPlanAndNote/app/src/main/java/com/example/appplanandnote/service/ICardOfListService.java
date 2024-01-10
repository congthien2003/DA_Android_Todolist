package com.example.appplanandnote.service;

import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.ListOfBoard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICardOfListService {
    @GET("/api/card")
    Call<List<CardOfList>> getCardsByListID(@Query("listID") int listID);

    @POST("/api/newcard")
    Call<CardOfList> createNewCard(@Body CardOfList newCard);
    @DELETE("/api/deletecard/{id}")
    Call<Void> deleteCard(@Path("id") int cardID);
    @PUT("/api/updatecard/{id}")
    Call<Void> updateCard(
            @Path("id") int cardID,
            @Body CardOfList card
    );
    @GET("/api/card/{id}")
    Call<List<CardOfList>> getCard(@Path("id") int cardID);

    @GET("/api/cardall")
    Call<List<CardOfList>> getCardALL();
}
