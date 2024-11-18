package com.example.easymeet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;

public class SignUpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        Button signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.usernameInput);
                String usernameTxt = String.valueOf(username.getText());
                EditText email = findViewById(R.id.emailInput);
                String emailTxt = String.valueOf(email.getText());
                EditText password = findViewById(R.id.passwordInput);
                String passwordTxt = String.valueOf(password.getText());
                EditText confirmPassword = findViewById(R.id.confirmPasswordInput);
                String confirmPasswordTxt = String.valueOf(confirmPassword.getText());
                if (passwordTxt.equals(confirmPasswordTxt)) {
                    System.out.println("Username: " + usernameTxt +
                            "\nEmail: " + emailTxt +
                            "\nPassword: " + passwordTxt +
                            "\nConfirm Password: " + confirmPasswordTxt);
                } else {
                    // Display a message or print an error
                    System.out.println("Passwords do not match!");
                   // Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
