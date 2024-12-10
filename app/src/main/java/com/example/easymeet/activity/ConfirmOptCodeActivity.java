package com.example.easymeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easymeet.R;


public class ConfirmOptCodeActivity extends AppCompatActivity {

    EditText confrimOptCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.opt_code_confirm);

        confrimOptCode = findViewById(R.id.editTextOtp);

        Button goToConfirmPassword = findViewById(R.id.buttonConfirm);
        goToConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch text inside onClick


                String confirmCodeTxt = confrimOptCode.getText().toString();
                Intent intentGet = getIntent();
                String confirmationCode = intentGet.getStringExtra("VERIFICATION_CODE");
                String getEmail = intentGet.getStringExtra("email");
                // Log both values for debugging
                Log.d("TAG is:", confirmationCode);
                Log.d("Entered text is:", confirmCodeTxt);

                // Add null check and compare strings safely
                if (confirmationCode != null && confirmationCode.equals(confirmCodeTxt)) {
                    Intent intent = new Intent(ConfirmOptCodeActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("email",getEmail);
                    startActivity(intent);
                } else {
                    Toast.makeText(ConfirmOptCodeActivity.this, "Wrong code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
