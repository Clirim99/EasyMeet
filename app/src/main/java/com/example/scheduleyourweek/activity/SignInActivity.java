package com.example.scheduleyourweek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scheduleyourweek.R;
import com.example.scheduleyourweek.repository.UserRepository;
import com.example.scheduleyourweek.utility.Authentication;
import com.example.scheduleyourweek.utility.EmailSender;

import java.util.Random;

import javax.mail.MessagingException;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private EditText username;
    private EditText password;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        // Initialize UI components
        username = findViewById(R.id.usernameinput);
        password = findViewById(R.id.passwordInput);
        forgotPassword = findViewById(R.id.forgotPasswordText);

        // Handle forgot password action
        forgotPassword.setOnClickListener(v -> {
            String email = username.getText().toString().trim();
            if(!UserRepository.doesUserExist(SignInActivity.this, email)){
                Toast.makeText(this, "This user doesn't exist", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validate email input
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email address.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send the email asynchronously
            sendForgotPasswordEmail(email);
        });

        // Handle login action
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(v -> {
            String usernameTxt = username.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();

            if (Authentication.logIn(SignInActivity.this, usernameTxt, passwordTxt)) {
                sendLoginEmail(usernameTxt);
                //Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                //startActivity(intent);
            } else {
                Toast.makeText(SignInActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
            }
            //sendLoginEmail(usernameTxt);
        });
    }

    /**
     * Sends a forgot password email with a verification code.
     *
     * @param email The email address of the user.
     */
    private void sendForgotPasswordEmail(String email) {
        new Thread(() -> {
            // Generate a 6-digit verification code
            String code = generateVerificationCode();

            try {
                // Attempt to send the email
                EmailSender.sendCode(email, code);

                // Update the UI on successful email sending
                runOnUiThread(() -> {
                    Toast.makeText(SignInActivity.this, "6-digit code sent to " + email, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Verification code sent to: " + email);

                    // Redirect to the verification screen
                    Intent intent = new Intent(SignInActivity.this, ConfirmOptCodeActivity.class);
                 //   intent.putExtra("USER_EMAIL", email);
                    intent.putExtra("VERIFICATION_CODE", code);
                    intent.putExtra("email",email);
                    startActivity(intent);
                });

            } catch (MessagingException e) {
                // Handle email sending failure
                Log.e(TAG, "Failed to send email: " + e.getMessage(), e);

                runOnUiThread(() -> {
                    Toast.makeText(SignInActivity.this, "Failed to send verification code. Please try again.", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void sendLoginEmail(String email) {
        new Thread(() -> {
            // Generate a 6-digit verification code
            String code = generateVerificationCode();

            try {
                // Attempt to send the email
                EmailSender.sendCode(email, code);

                // Update the UI on successful email sending
                runOnUiThread(() -> {
                    Toast.makeText(SignInActivity.this, "6-digit code sent to " + email, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Verification code sent to: " + email);

                    // Redirect to the verification screen
                    Intent intent = new Intent(SignInActivity.this, ConfirmIdentity.class);
                    //   intent.putExtra("USER_EMAIL", email);
                    intent.putExtra("VERIFICATION_CODE", code);
                    intent.putExtra("email",email);
                    startActivity(intent);
                });

            } catch (MessagingException e) {
                // Handle email sending failure
                Log.e(TAG, "Failed to send email: " + e.getMessage(), e);

                runOnUiThread(() -> {
                    Toast.makeText(SignInActivity.this, "Failed to send verification code. Please try again.", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
    /**
     * Generates a 6-digit verification code.
     *
     * @return A randomly generated 6-digit code as a String.
     */
    private String generateVerificationCode() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // Generate a 6-digit random code
    }
}
