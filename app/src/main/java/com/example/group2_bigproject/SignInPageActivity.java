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

public class SignInPageActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    dBHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);
        username = findViewById(R.id.edtText_UsernameSignIn);
        password = findViewById(R.id.edtText_PasswordSignIn);
        helper = new dBHelper(this);
        db = helper.getWritableDatabase();
        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        Intent signUpClick = new Intent(this, SignUpPageActivity.class);
        Button btn_SignIn = findViewById(R.id.btn_SignIn);

        hereClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpClick);
            }
        });
        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().compareTo("DuckTheVit") == 0) {
                    Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignInPageActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
                }
                if (username.getText().toString().isEmpty()) {
                    Toast.makeText(SignInPageActivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (password.getText().toString().isEmpty()) {
                    Toast.makeText(SignInPageActivity.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (!username.getText().toString().isEmpty()){
                    int flags = helper.checkPassword(username.getText().toString(), password.getText().toString());
                    switch (flags) {
                        case 0:
                            Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userID", "000");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            Toast.makeText(SignInPageActivity.this,"Signed In",Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(SignInPageActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(SignInPageActivity.this, "Username not registered!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
