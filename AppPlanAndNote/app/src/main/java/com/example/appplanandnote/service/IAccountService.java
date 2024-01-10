package com.example.appplanandnote.service;

import com.example.appplanandnote.model.AppUser;
import com.example.appplanandnote.model.ForgotPasswordResponse;
import com.example.appplanandnote.model.RegistrationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAccountService {
    @POST("/app/checkLogin")
    @FormUrlEncoded
    Call<List<AppUser>> checkLogin(
            @Field("email") String email,
            @Field("userPassword") String userPassword
    );

    @POST("/app/register")
    Call<RegistrationResponse> register(@Body AppUser request);

    @FormUrlEncoded
    @POST("/app/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("/app/changePassword")
    Call<Void> changePassword(
            @Field("userID") int userID,
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword
    );
}
