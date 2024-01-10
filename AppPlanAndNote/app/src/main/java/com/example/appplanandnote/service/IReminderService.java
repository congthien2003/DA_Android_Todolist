package com.example.appplanandnote.service;

import com.example.appplanandnote.model.CardOfList;
import com.example.appplanandnote.model.Reminder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReminderService {
    @POST("/api/newreminder")
    Call<Reminder> createNewReminder(@Body Reminder newReminder);
    @GET("/api/reminders/{userID}")
    Call<List<Reminder>> getReminders(@Path("userID") int userID);
}
