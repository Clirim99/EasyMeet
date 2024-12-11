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
import com.example.easymeet.repository.UserRepository;
import com.example.easymeet.utility.EncryptData;

public class ChangePasswordActivity extends AppCompatActivity {

    Button confirmButton;
    EditText changePassword;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.change_password_activity);

        Intent intentGet = getIntent();
        String getEmail = intentGet.getStringExtra("email");

        changePassword = findViewById(R.id.changedPassword);
        confirmPassword = findViewById(R.id.changedPasswordConfirm);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = changePassword.getText().toString().trim();
                String confirmNewPassword = confirmPassword.getText().toString().trim();

                if (newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmNewPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hashedNewPassword = EncryptData.md5Hasshing(newPassword);
                boolean isUpdated = UserRepository.updatePassword(ChangePasswordActivity.this, getEmail, hashedNewPassword);
                if (isUpdated) {
                    Toast.makeText(ChangePasswordActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Failed to update password. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
