package com.example.group2_bigproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPageActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private dBHelper helper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        username = findViewById(R.id.edtText_Username);
        email = findViewById(R.id.edtText_Email);
        password = findViewById(R.id.edtText_Password);
        confirmPassword = findViewById(R.id.edtText_ConfirmPassword);
        helper = new dBHelper(this);
        db = helper.getWritableDatabase();

        Button btn_SignUp = findViewById(R.id.btn_SignUp);

        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        hereClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.checkConflictedUsername(username.getText().toString())) {
                    Toast.makeText(SignUpPageActivity.this, "Username is taken!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().compareTo(confirmPassword.getText().toString()) > 0) {
                    Toast.makeText(SignUpPageActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                } else {
                    helper.addUser(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    Intent intent = new Intent(SignUpPageActivity.this, SignInPageActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignUpPageActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
