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
import android.util.Log;


public class ConfirmOptCodeActivity extends AppCompatActivity  {


    EditText confrimOptCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.opt_code_confirm);

        confrimOptCode = findViewById(R.id.editTextOtp);

        Button goToSignUpBtn = findViewById(R.id.buttonConfirm);
        goToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            String confirmCodeTxt = String.valueOf(confrimOptCode.getText());
            Intent intent = getIntent();
            String confirmationCode = intent.getStringExtra("VERIFICATION_CODE");

            @Override
            public void onClick(View v) {
                Log.d("TAG is:",  confirmationCode);

                if (confirmationCode.equals(confirmCodeTxt)) {
                    Intent intent = new Intent(ConfirmOptCodeActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ConfirmOptCodeActivity.this, "wrong code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
