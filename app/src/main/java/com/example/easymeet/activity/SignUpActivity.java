package com.example.easymeet.activity;

import static com.example.easymeet.utility.EncryptData.md5Hasshing;

import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;
import com.example.easymeet.model.User;
import com.example.easymeet.utility.Authentication;
import com.example.easymeet.utility.EncryptData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText email;
    EditText password;
    EditText confirmPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        firstName = findViewById(R.id.nameInput);
        lastName = findViewById(R.id.lastnameInput);
        username = findViewById(R.id.usernameinput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);


        Button signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameTxt = String.valueOf(firstName.getText());
                String lastNameTxt = String.valueOf(lastName.getText());
                String usernameTxt = String.valueOf(username.getText());
                String emailTxt = String.valueOf(email.getText());
                String passwordTxt = String.valueOf(password.getText());
                String confirmPasswordTxt = String.valueOf(confirmPassword.getText());
                User user = new User(firstNameTxt,lastNameTxt,usernameTxt,emailTxt,passwordTxt,confirmPasswordTxt);

                if (!isPasswordValid(passwordTxt)) {
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long, contain 1 number, 1 special character, and 1 uppercase letter.", Toast.LENGTH_LONG).show();
                    return; // Stop further processing if password is invalid
                }

                // Check if passwords match
                if (!passwordTxt.equals(confirmPasswordTxt)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // You can add more validation for the email and username if needed
                if (usernameTxt.isEmpty() || emailTxt.isEmpty() || firstNameTxt.isEmpty() || lastNameTxt.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Firstname , lastname, username and email are required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hashedPassword = EncryptData.md5Hasshing(passwordTxt);

                if (hashedPassword == null) {
                    Toast.makeText(SignUpActivity.this, "An error occurred while hashing the password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If everything is valid, proceed
                System.out.println("FirstName: " + firstNameTxt +
                        "\nLastname: " + lastNameTxt +
                        "\nUsername: " + usernameTxt +
                        "\nEmail: " + emailTxt +
                        "\nPassword: " + hashedPassword +
                        "\nConfirm Password: " + hashedPassword
                );
                if (Authentication.signUp(SignUpActivity.this, user)){
                // Start SignInActivity
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Registering failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean isPasswordValid(String password) {
        // Password should be at least 8 characters long, contain at least one digit, one special character, and one uppercase letter
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}|:;,.<>?])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
//    private String hashPassword(String password) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashedBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public static String md5Hasshing(String s) {
//        try {
//            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}


