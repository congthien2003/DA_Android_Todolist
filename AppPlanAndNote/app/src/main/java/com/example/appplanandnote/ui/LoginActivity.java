package com.example.appplanandnote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.AccountDAO;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignup);
        TextView txtForgotPassword = findViewById(R.id.txtForgotPassword);

        //Xử lý sự kiện đăng nhập - DONE
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(inputEmail.getText().toString().isEmpty() || inputPassword.getText().toString().isEmpty()){

                        throw new Exception("Không được để trống Email hoặc Password");
                    }
                    AccountDAO temp = new AccountDAO(LoginActivity.this);
                    temp.CheckUser(inputEmail.getText().toString(), inputPassword.getText().toString());



                }catch (Exception ex){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage(ex.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }

            }
        });

        //Xử lý sự kiện đăng ký - DONE
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Xử lý sự kiện quên mật khẩu
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

}