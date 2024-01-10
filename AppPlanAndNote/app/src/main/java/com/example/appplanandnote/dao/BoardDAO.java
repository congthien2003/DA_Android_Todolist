package com.example.appplanandnote.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appplanandnote.MainActivity;
import com.example.appplanandnote.adapter.BoardAdapter;
import com.example.appplanandnote.model.Board;
import com.example.appplanandnote.service.IBoardService;
import com.example.appplanandnote.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDAO {
    private Context context;
    private BoardAdapter adapter;
    private List<Board> boardList;
    public BoardDAO(){}
    public BoardDAO(Context context){
        this.context = context;
    }
    public BoardDAO(Context context, BoardAdapter adapter, List<Board> boardList){
        this.context = context;
        this.adapter = adapter;
        this.boardList = boardList;
    }

    public void AddBoard (Board board){
        Call<Board> call = RetrofitInstance.getRetrofitInstance().create(IBoardService.class).createNewBoard(board);
        call.enqueue(new Callback<Board>() {
            @Override
            public void onResponse(Call<Board> call, Response<Board> response) {
                if(response.isSuccessful()){

                    Board b = response.body();
                    boardList.add(b);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(boardList.size()-1);
                    Toast.makeText(context, "THÊM BOARD THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Board> call, Throwable t) {

            }
        });

    }
    public void DeleteBoard(int boardID){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(IBoardService.class).deleteBoard(boardID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "XÓA BOARD THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void UpdateBoard(int boardID, Board board){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(IBoardService.class).updateBoard(boardID, board);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                    Toast.makeText(context, "CẬP NHẬT BOARD THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }


}
