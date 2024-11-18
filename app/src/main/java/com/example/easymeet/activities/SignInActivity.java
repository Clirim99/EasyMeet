package com.example.easymeet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.easymeet.R;

public class SignInActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.usernameinput);
                String usernameTxt = String.valueOf(username.getText());
                EditText password = findViewById(R.id.passwordInput);
                String passwordTxt = String.valueOf(password.getText());
                System.out.println("Username: " + usernameTxt + "\nPassword: " + passwordTxt);
            }
        });

    }



}