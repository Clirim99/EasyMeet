package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;

public class ChangePasswordActivity extends AppCompatActivity {

    Button confirmButton;
    EditText changePassword;
    EditText confirmPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
         changePassword = findViewById(R.id.changedPassword);
         confirmPassword = findViewById(R.id.changedPasswordConfirm);
         confirmButton = findViewById(R.id.confirmButton);

         confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }


}
