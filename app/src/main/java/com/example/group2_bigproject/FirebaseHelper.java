package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirebaseHelper {
    private FirebaseFirestore db;
    private Context context;
    public FirebaseHelper(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public void searchForFriendRequestList(String userID, FriendRequestSearch friendRequestSearch) {
        CollectionReference dbFriendRequestList = db.collection("FriendRequests");
        dbFriendRequestList.whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               friendRequest friendRequest = new friendRequest();
               for (QueryDocumentSnapshot document : task.getResult()) {
                   friendRequest = document.toObject(com.example.group2_bigproject.friendRequest.class);
                   friendRequestSearch.FriendRequestList(friendRequest);
               }
           }
        });
    }
    public void saveRouteToRouteHistory(Route route){
        CollectionReference dbRunHistory = db.collection("routeHistory");
        dbRunHistory.add(route).addOnCompleteListener(task -> {
//            Toast.makeText(context, "Route saved successfully on Firebase!!!", Toast.LENGTH_SHORT).show();
        });
    }

    /*
    this method read the user profile, to interact with the user profile that was read,
    modify the lambda function to your liking.
     */
    public void readUser(String userID, MyCallback myCallback ) {
        CollectionReference dbUsers = db.collection("users");
        dbUsers.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getId().compareTo(userID) == 0) {
                        myCallback.onCallback(document.toObject(com.example.group2_bigproject.User.class));
                    }
                }
            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    public void readRoutes(String userID, RouteReader routeReader) {
        CollectionReference dbRoutes = db.collection("routeHistory");
        dbRoutes.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Route> routes = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getId().compareTo(userID) == 0)
                    Log.d("ADDED ROUTE", document.getId());
                }
                routeReader.readRoute(routes);
            }
        });
    }

    public void updateUser(String name, String gender, String birthday, String phone, String address, String height, String weight, String userID) {
        CollectionReference dbUsers = db.collection("users");
        dbUsers.document(userID).update("name", name,
                                            "gender", gender,
                                            "birthday", birthday,
                                            "phone", phone,
                                            "address", address,
                                            "height", height,
                                            "weight", weight
        ).addOnCompleteListener(task -> {
            Log.d("fbHelper", "edit profile successful");
        }).addOnFailureListener(e -> {
            Log.e("fbHelper", "edit profile failed");
        });
    }

    public void searchForUser(String input, userSearcher userSearcher) {
        CollectionReference dbUsers = db.collection("users");
        dbUsers.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> users = new ArrayList<>();
                for(QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("FBHELPER", "userNAME IS " + document.toObject(User.class).username);
                    if (document.toObject(User.class).username.contains(input)) {
                        users.add(document.toObject(User.class));
                        Log.d("FBHELPER", "ADDED!");
                    }
                }
                userSearcher.searchUser(users);
            }
        });
    }
}
