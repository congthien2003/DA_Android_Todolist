package com.example.appplanandnote.dao;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.appplanandnote.adapter.ListAdapter;
import com.example.appplanandnote.model.Board;
import com.example.appplanandnote.model.ListOfBoard;
import com.example.appplanandnote.service.IListOfBoardService;
import com.example.appplanandnote.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDAO {

    private List<ListOfBoard> lists;
    ArrayAdapter<ListOfBoard> adapterList;
    private Context context;

    private ListAdapter adapter;
    public ListDAO(){
    }
    public ListDAO(List<ListOfBoard> lists, Context context, ArrayAdapter<ListOfBoard> adapterList){
        this.lists = lists;
        this.context = context;
        this.adapterList = adapterList;
    }
    public ListDAO(List<ListOfBoard> lists, Context context){
        this.lists = lists;
        this.context = context;
    }
    public ListDAO(List<ListOfBoard> lists, Context context, ListAdapter adapter){
        this.lists = lists;
        this.context = context;
        this.adapter = adapter;
    }
    public void GetListByBoardID (int boardID){
        Call<List<ListOfBoard>> call = RetrofitInstance.getRetrofitInstance().create(IListOfBoardService.class).getListItems(boardID);
        call.enqueue(new Callback<List<ListOfBoard>>() {
            @Override
            public void onResponse(Call<List<ListOfBoard>> call, Response<List<ListOfBoard>> response) {
                if(response.isSuccessful()){
                    lists.clear();
                    lists.addAll(response.body());
//                    if(adapter != null)
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(context, "LỖI KHI TẢI LIST", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListOfBoard>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void GetListByBoardID_List (int boardID){
        Call<List<ListOfBoard>> call = RetrofitInstance.getRetrofitInstance().create(IListOfBoardService.class).getListItems(boardID);
        call.enqueue(new Callback<List<ListOfBoard>>() {
            @Override
            public void onResponse(Call<List<ListOfBoard>> call, Response<List<ListOfBoard>> response) {
                if(response.isSuccessful()){
                    lists.clear();
                    lists.addAll(response.body());
//                    if(adapter != null)
                    adapterList.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(context, "LỖI KHI TẢI LIST", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ListOfBoard>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void UpdateList (int listID, String listName){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(IListOfBoardService.class).updateList(listID, listName);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void DeleteList(int listID){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(IListOfBoardService.class).deleteList(listID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "XÓA LIST THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void AddList (ListOfBoard newList){
        Call<ListOfBoard> call = RetrofitInstance.getRetrofitInstance().create(IListOfBoardService.class).createNewList(newList);
        call.enqueue(new Callback<ListOfBoard>() {
            @Override
            public void onResponse(Call<ListOfBoard> call, Response<ListOfBoard> response) {
                if(response.isSuccessful()){
                    ListOfBoard l = response.body();
                    lists.add(l);
                    //adapter.notifyDataSetChanged();
                    adapterList.notifyDataSetChanged();
                    Toast.makeText(context, "THÊM LIST THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ListOfBoard> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });

    }
}
