package com.example.appplanandnote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.AccountDAO;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText inputYourEmail = findViewById(R.id.inputYourEmail);
        Button btnConfirmEmailForgot = findViewById(R.id.btnConfirmEmailForgot);
        Button btnBackForgot = findViewById(R.id.btnBackForgot);
        //Xử lý sự kiện Confirm Email - DONE
        btnConfirmEmailForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(inputYourEmail.getText().toString().isEmpty())
                        throw new Exception("KHÔNG ĐƯỢC ĐỂ TRỐNG EMAIL");
                    AccountDAO temp = new AccountDAO(ForgotPasswordActivity.this);
                    temp.ForgotPassword(inputYourEmail.getText().toString());

                }catch (Exception ex){
                    new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setMessage(ex.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
        btnBackForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}