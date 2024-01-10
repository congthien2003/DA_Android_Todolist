package com.example.appplanandnote.service;

import com.example.appplanandnote.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IBoardService {

    @POST("/api/newboard")
    Call<Board> createNewBoard(
            @Body Board board
    );

    @DELETE("/api/deleteboard/{id}")
    Call<Void> deleteBoard(
            @Path("id") int boardID
    );

    @PUT("/api/updateboard/{id}")
    Call<Void> updateBoard(@Path("id") int boardID, @Body Board board);
    @GET("/api/boardsByUserId/{userID}")
    Call<List<Board>> getBoardsByUserId(@Path("userID") int userID);
    @GET("/api/board")
    Call<List<Board>> getAllBoards();

    @GET("/api/board/{id}")
    Call<List<Board>> getBoardById(
            @Path("id") int boardID
    );
}
