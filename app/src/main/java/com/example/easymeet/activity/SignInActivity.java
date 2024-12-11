package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.repository.UserRepository;
import com.example.easymeet.utility.Authentication;
import com.example.easymeet.utility.EmailSender;
import com.example.easymeet.utility.EncryptData;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Random;

import javax.mail.MessagingException;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private EditText username;
    private EditText password;
    private TextView forgotPassword;
    private TextView createNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        // Initialize UI components
        username = findViewById(R.id.usernameinput);
        password = findViewById(R.id.passwordInput);
        forgotPassword = findViewById(R.id.forgotPasswordText);
        createNewAccount = findViewById(R.id.gotoSignUpEditText);
        // Handle forgot password action

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to effectively disable the back button
            }
        });
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

        createNewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
                    // Handle login action
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(v -> {
            String usernameTxt = username.getText().toString().trim();
            String passwordTxt = EncryptData.md5Hasshing(password.getText().toString().trim());

            if (Authentication.logIn(SignInActivity.this, usernameTxt, passwordTxt)) {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.i("MYTAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            Log.i("MYTAG", "FCM Token: " + token);

                            // You can store this token in your backend if you want to send notifications to this specific device later
                        });

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
