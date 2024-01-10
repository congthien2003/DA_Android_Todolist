package com.example.appplanandnote.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.appplanandnote.adapter.CardLoadCallback;
import com.example.appplanandnote.adapter.ReminderAdapter;
import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.Reminder;
import com.example.appplanandnote.service.IReminderService;
import com.example.appplanandnote.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderDAO {

    private Context context;
    private List<Reminder> reminders;
    private ReminderAdapter adapter;
    List<CardOfList> cards;
    List<CardOfList> temps;
    public ReminderDAO(Context context){
        this.context = context;
    }
    public ReminderDAO(Context context, List<Reminder> reminders, ReminderAdapter adapter){
        this.context = context;
        this.reminders = reminders;
        this.adapter = adapter;
    }
    public ReminderDAO(Context context, List<Reminder> reminders, ReminderAdapter adapter, List<CardOfList> c, List<CardOfList> temps){
        this.context = context;
        this.reminders = reminders;
        this.adapter = adapter;
        this.cards = c;
        this.temps = temps;
    }
    public void AddReminder(Reminder reminder){
        Call<Reminder> call = RetrofitInstance.getRetrofitInstance().create(IReminderService.class).createNewReminder(reminder);
        call.enqueue(new Callback<Reminder>() {
            @Override
            public void onResponse(Call<Reminder> call, Response<Reminder> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "THÊM LỜI NHẮC THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reminder> call, Throwable t) {

            }
        });
    }
    public void GetReminderByUserID(int userID){
        Call<List<Reminder>> call = RetrofitInstance.getRetrofitInstance().create(IReminderService.class).getReminders(userID);
        call.enqueue(new Callback<List<Reminder>>() {
            @Override
            public void onResponse(Call<List<Reminder>> call, Response<List<Reminder>> response) {
                if(response.isSuccessful()){
                    reminders.clear();
                    reminders.addAll(response.body());
                    //CardDAO cardDAO = new CardDAO(cards);
//                    cardDAO.setCardLoadCallback(new CardLoadCallback() {
//                        @Override
//                        public void onCardLoaded() {
//                            adapter.notifyDataSetChanged();
//                            Toast.makeText(context, "TẢI LỜI NHẮC THÀNH CÔNG", Toast.LENGTH_LONG).show();
//                        }
//                    });

                    for(Reminder r : reminders){
                        for(CardOfList c : cards){
                            if(r.getCardID() == c.getCardID()){
                                temps.add(c);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                    Toast.makeText(context, "TẢI LỜI NHẮC THÀNH CÔNG", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Reminder>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(context, "LỖI KHI LẤY REMINDER", Toast.LENGTH_LONG).show();
            }
        });
    }

}
