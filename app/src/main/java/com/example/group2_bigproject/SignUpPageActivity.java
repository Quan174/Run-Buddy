package com.example.group2_bigproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashSet;

public class SignUpPageActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        username = findViewById(R.id.edtText_Username);
        email = findViewById(R.id.edtText_Email);
        password = findViewById(R.id.edtText_Password);
        confirmPassword = findViewById(R.id.edtText_ConfirmPassword);
        db = FirebaseFirestore.getInstance();
        Button btn_SignUp = findViewById(R.id.btn_SignUp);

        TextView hereClickView = findViewById(R.id.textView_HereClicker);
        hereClickView.setOnClickListener(v -> finish());

        btn_SignUp.setOnClickListener(v -> {
            CollectionReference dbUsers = db.collection("users");
            dbUsers.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentUsername = document.toObject(User.class).username;
                        String documentEmail = document.toObject(User.class).email;
                        if(documentUsername.compareTo(username.getText().toString()) == 0) {
                            Toast.makeText(this, "Username existed!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (documentEmail.compareTo(email.getText().toString()) == 0) {
                            Toast.makeText(this, "Email existed!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (confirmPassword.getText().toString().compareTo(password.getText().toString()) != 0) {
                        Toast.makeText(this, "Password missmatch!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    User user = new User(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    dbUsers.add(user).addOnSuccessListener(documentReference -> {
                        friendRequest friendRequest = new friendRequest(documentReference.getId(), user.username);
                        CollectionReference dbFriendRequestList = db.collection("FriendRequests");
                        dbFriendRequestList.add(friendRequest).addOnCompleteListener(task1 -> {
                            CollectionReference dbFriendList = db.collection("FriendList");
                            FriendList friendList = new FriendList(documentReference.getId(), user.username);
                            dbFriendList.add(friendList).addOnCompleteListener(task2 -> {
                                Toast.makeText(this, "user added to firebase", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpPageActivity.this, SignInPageActivity.class);
                                startActivity(intent);
                            });
                        });
                    });
                } else {
                    Toast.makeText(SignUpPageActivity.this, "Error checking users", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
