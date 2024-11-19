package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.utility.Authentication;

public class SignInActivity extends AppCompatActivity {

    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        username = findViewById(R.id.usernameinput);
        password = findViewById(R.id.passwordInput);


        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTxt = String.valueOf(username.getText());
                String passwordTxt = String.valueOf(password.getText());
                System.out.println("Username: " + usernameTxt + "\nPassword: " + passwordTxt);

                if(Authentication.logIn(SignInActivity.this, usernameTxt , passwordTxt))
                {
                    Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SignInActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}