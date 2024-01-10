package com.example.appplanandnote.service;

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

public interface IListOfBoardService {
    @GET("/api/list")
    Call<List<ListOfBoard>> getListItems(@Query("boardID") int boardID);
    @PUT("/api/updatelist/{id}")
    Call<Void> updateList(
            @Path("id") int listID,
            @Query("listName") String listName
    );


    @DELETE("/api/deletelist/{id}")
    Call<Void> deleteList(@Path("id") int listID);

    @POST("/api/newlist")
    Call<ListOfBoard> createNewList(@Body ListOfBoard newList);
}
