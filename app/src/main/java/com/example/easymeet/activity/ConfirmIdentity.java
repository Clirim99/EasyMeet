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

public class ConfirmIdentity extends AppCompatActivity {

    EditText confrimOptCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.confirm_your_identity);

        confrimOptCode = findViewById(R.id.editTextOtp);

        Button goToHomeActivity = findViewById(R.id.buttonConfirm);
        goToHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch text inside onClick


                String confirmCodeTxt = confrimOptCode.getText().toString();
                Intent intentGet = getIntent();
                String confirmationCode = intentGet.getStringExtra("VERIFICATION_CODE");
                // Log both values for debugging
                Log.d("TAG is:", confirmationCode);
                Log.d("Entered text is:", confirmCodeTxt);

                // Add null check and compare strings safely
                if (confirmationCode != null && confirmationCode.equals(confirmCodeTxt)) {
                    Intent intent = new Intent(ConfirmIdentity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ConfirmIdentity.this, "Wrong code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
