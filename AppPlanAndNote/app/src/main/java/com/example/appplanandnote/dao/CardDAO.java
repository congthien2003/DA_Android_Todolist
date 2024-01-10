package com.example.appplanandnote.dao;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appplanandnote.adapter.CardAdapter;
import com.example.appplanandnote.adapter.CardLoadCallback;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.ListOfBoard;
import com.example.appplanandnote.service.ICardOfListService;
import com.example.appplanandnote.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDAO {


    private List<CardOfList> cards;
    private MutableLiveData<List<CardOfList>> cardsLiveData = new MutableLiveData<>();

    public LiveData<List<CardOfList>> getCardLiveData() {
        return cardsLiveData;
    }
    private Context context;
    private CardAdapter adapter;
    public CardOfList temp;
    private CardLoadCallback callback;
    public void setCardLoadCallback(CardLoadCallback callback) {
        this.callback = callback;
    }

    public CardDAO(List<CardOfList> cards){
        this.cards = cards;
    }
    public CardDAO(){

    }
    public CardDAO(Context context){
        this.context = context;
    }
    public CardDAO(List<CardOfList> cards, Context context){
        this.cards = cards;
        this.context = context;
    }

    public CardDAO(List<CardOfList> cards, Context context, CardAdapter adapter){
        this.cards = cards;
        this.context = context;
        this.adapter = adapter;
    }

    public void GetCardByListID(int listID){
        Call<List<CardOfList>> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).getCardsByListID(listID);
        call.enqueue(new Callback<List<CardOfList>>() {
            @Override
            public void onResponse(Call<List<CardOfList>> call, Response<List<CardOfList>> response) {
                if(response.isSuccessful()){
                    cards.clear();
                    cards.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "TẢI CARD THÀNH CÔNG", Toast.LENGTH_LONG).show();

                }
                else {
                    cards.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "CHƯA CÓ CARD TRONG LIST", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CardOfList>> call, Throwable t) {

            }
        });
    }

    public void AddCard(CardOfList card){
        Call<CardOfList> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).createNewCard(card);
        call.enqueue(new Callback<CardOfList>() {
            @Override
            public void onResponse(Call<CardOfList> call, Response<CardOfList> response) {
                if(response.isSuccessful()){
                    cards.add(response.body());
                    String temp = response.body().getCardName();
                    adapter.notifyItemInserted(cards.size()-1);
                    Toast.makeText(context, "THÊM THÀNH CÔNG CARD MỚI", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CardOfList> call, Throwable t) {

            }
        });
    }

    public void DeleteCard(int cardID){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).deleteCard(cardID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "XÓA THÀNH CÔNG CARD", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void UpdateCard(int cardID, CardOfList c){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).updateCard(cardID, c);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    public void GetCardBycardID (int cardID){
        Call<List<CardOfList>> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).getCard(cardID);
        call.enqueue(new Callback<List<CardOfList>>() {
            @Override
            public void onResponse(Call<List<CardOfList>> call, Response<List<CardOfList>> response) {
                if(response.isSuccessful()){
                    List<CardOfList> l_ = new ArrayList<>();
                    l_.addAll(response.body());
                    cards.add(l_.get(0));
//                    if (callback != null) {
//                        callback.onCardLoaded();
//                    }

                }
            }

            @Override
            public void onFailure(Call<List<CardOfList>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }
    public void GetCardAll(){
        Call<List<CardOfList>> call = RetrofitInstance.getRetrofitInstance().create(ICardOfListService.class).getCardALL();
        call.enqueue(new Callback<List<CardOfList>>() {
            @Override
            public void onResponse(Call<List<CardOfList>> call, Response<List<CardOfList>> response) {
                if(response.isSuccessful()){
                    cards.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CardOfList>> call, Throwable t) {

            }
        });
    }
}
