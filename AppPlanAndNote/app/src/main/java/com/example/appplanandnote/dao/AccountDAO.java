package com.example.appplanandnote.dao;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.appplanandnote.MainActivity;
import com.example.appplanandnote.model.AppUser;
import com.example.appplanandnote.model.ForgotPasswordResponse;
import com.example.appplanandnote.model.RegistrationResponse;
import com.example.appplanandnote.service.IAccountService;
import com.example.appplanandnote.service.RetrofitInstance;
import com.example.appplanandnote.ui.SignUpActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDAO {

    private Context context;

    public AccountDAO(Context context) {
        this.context = context;
    }
    public void RegisterAccount (AppUser user){
        Call<RegistrationResponse> call = RetrofitInstance.getRetrofitInstance().create(IAccountService.class).register(user);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "ĐĂNG KÍ THÀNH CÔNG", Toast.LENGTH_LONG).show();
                    ((SignUpActivity) context).finish();
                }
                else {
                    Toast.makeText(context, "ĐÃ TỒN TẠI TÀI KHOẢN TRONG HỆ THỐNG", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
            }
        });
    }

    public void CheckUser(String email, String userPassword){

        Call<List<AppUser>> call = RetrofitInstance.getRetrofitInstance().create(IAccountService.class).checkLogin(email, userPassword);
        call.enqueue(new Callback<List<AppUser>>() {
            @Override
            public void onResponse(Call<List<AppUser>> call, Response<List<AppUser>> response) {
                if(response.isSuccessful()){
                    AppUser user = response.body().get(0);
                    Toast.makeText(context, "ĐĂNG NHẬP THÀNH CÔNG", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userID", user.getUserID());
                    intent.putExtra("email", user.getEmail());
                    context.startActivity(intent);

                }
                else{
                    String errorMessage = "SAI TÀI KHOẢN HOẶC MẬT KHẨU";
//                    if (response.errorBody() != null) {
//                        try {
//                            errorMessage = response.errorBody().string();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<AppUser>> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(context, "ĐĂNG NHẬP THẤT BẠI", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void ForgotPassword(String email){
        Call<ForgotPasswordResponse> call = RetrofitInstance.getRetrofitInstance().create(IAccountService.class).forgotPassword(email);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "MẬT KHẨU MỚI ĐÃ ĐƯỢC GỬI TỚI EMAIL CỦA BẠN", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "KHÔNG TỒN TẠI EMAIL CỦA BẠN TRONG HỆ THỐNG", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(context, "LỖI KẾT NỐI", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ChangePassword(int userID, String oldPassword, String newPassword){
        Call<Void> call = RetrofitInstance.getRetrofitInstance().create(IAccountService.class).changePassword(userID, oldPassword, newPassword);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "MẬT KHẨU ĐƯỢC CẬP NHẬT THÀNH CÔNG", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context, "SAI MẬT KHẨU CŨ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(context, "LỖI KẾT NỐI", Toast.LENGTH_LONG).show();
            }
        });
    }
}
