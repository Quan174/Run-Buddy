package com.example.group2_bigproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInPageActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private FirebaseFirestore db;
    private SharedPreferencesHelper spHeler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);
        username = findViewById(R.id.edtText_UsernameSignIn);
        password = findViewById(R.id.edtText_PasswordSignIn);
        db = FirebaseFirestore.getInstance();
        spHeler = new SharedPreferencesHelper(this);
        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        Intent signUpClick = new Intent(this, SignUpPageActivity.class);
        Button btn_SignIn = findViewById(R.id.btn_SignIn);

        spHeler.endSession();
        hereClickView.setOnClickListener(v -> startActivity(signUpClick));
        btn_SignIn.setOnClickListener(v -> {
            CollectionReference dbUsers = db.collection("users");
            dbUsers.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentUsername = document.toObject(User.class).username;
                        if (documentUsername.compareTo(username.getText().toString()) == 0) {
                            String documentPassword = document.toObject(User.class).password;
                            if (documentPassword.compareTo(password.getText().toString()) == 0) {
                                spHeler.startSession(document.getId());
                                Toast.makeText(this, spHeler.getSessionID(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInPageActivity.this, HomePageActivity.class);
                                startActivity(intent);
                                return;
                            } else {
                                Toast.makeText(this, "Wrong password!!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    Toast.makeText(this, "Username doesn't exist! Register?", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
