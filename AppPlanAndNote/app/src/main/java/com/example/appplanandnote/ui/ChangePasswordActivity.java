package com.example.appplanandnote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appplanandnote.R;
import com.example.appplanandnote.dao.AccountDAO;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText inputOldPassword = findViewById(R.id.inputOldPassword);
        EditText inputNewPassword = findViewById(R.id.inputNewPassword);
        EditText inputConfirmPassword = findViewById(R.id.inputConfirmPassword);

        Button btnConfirmChangePassword = findViewById(R.id.btnConfirmChangePassword);
        Button btnBackChangePassword = findViewById(R.id.btnBackChangePassword);

        btnConfirmChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(inputOldPassword.getText().toString().isEmpty() || inputNewPassword.getText().toString().isEmpty()){

                        throw new Exception("Không được để trống thông tin");
                    }
                    if(!inputNewPassword.getText().toString().equals(inputConfirmPassword.getText().toString()))
                        throw new Exception("Xác nhận Password không giống nhau");
                    AccountDAO temp = new AccountDAO(ChangePasswordActivity.this);
                    int userID = getIntent().getExtras().getInt("KEY_userID");
                    temp.ChangePassword(userID, inputOldPassword.getText().toString(), inputNewPassword.getText().toString());


                }catch (Exception ex){
                    new AlertDialog.Builder(ChangePasswordActivity.this)
                            .setMessage(ex.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });
        btnBackChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}