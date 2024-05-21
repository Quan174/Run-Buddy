package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
                    if (document.toObject(Route.class).routeID.compareTo(userID) == 0)
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

    public void searchForUser(String userID, String input, userSearcher userSearcher) {
        CollectionReference dbUsers = db.collection("users");
        dbUsers.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> users = new ArrayList<>();
                for(QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("FBHELPER", "userNAME IS " + document.toObject(User.class).username);
                    if (document.getId().compareTo(userID) == 0) {
                        continue;
                    }
                    if (document.toObject(User.class).username.contains(input)) {
                        users.add(document.toObject(User.class));
                        Log.d("FBHELPER", "ADDED!");
                    }
                }
                userSearcher.searchUser(users);
            }
        });
    }

    public void addFriendRequest(User sender, String username) {
        CollectionReference dbFriendRequest = db.collection("FriendRequests");
        dbFriendRequest.whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendRequestList = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document: task.getResult()) {
                    friendRequestList = document.toObject(friendRequest.class).requestListID;
                    documentID = document.getId();
                }
                friendRequestList.add(sender);
                if (documentID != null){
                    dbFriendRequest.document(documentID).update("requestListID", friendRequestList);
                } else {
                    Log.d("FBHELPER", "ADD FRIEND REQUEST FAILED");
                }
            }
        });
    }

    public void removeFriendRequest(User sender, String username) {
        CollectionReference dbFriendRequest = db.collection("FriendRequests");
        Log.d("Remove friend request called", "username is " + username);
        dbFriendRequest.whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendRequestList = new ArrayList<>();
                ArrayList<User> updatedFriendRequestList = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document: task.getResult()) {
                    friendRequestList = document.toObject(friendRequest.class).requestListID;
                    documentID = document.getId();
                }
                if (documentID != null){
                    for (User user : friendRequestList) {
                        Log.d("REMOVE FRIEND", "FRIEND USERNAME IS " + user.username + " SENDER USERNAME IS " + sender.username);
                        if (user.isEqual(sender) == 0) {
                            continue;
                        }
                        updatedFriendRequestList.add(user);
                    }
                    dbFriendRequest.document(documentID).update("requestListID", updatedFriendRequestList);
                } else {
                    Log.d("FBHELPER", "REMOVE FRIEND REQUEST FAILED");
                }
            }
        });
    }

    public void addFriend(User requester, User receiver) {
        CollectionReference dbFriendList = db.collection("FriendList");
        dbFriendList.whereEqualTo("username", receiver.username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendList = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document: task.getResult()) {
                    friendList = document.toObject(FriendList.class).FriendList;
                    documentID = document.getId();
                }

                friendList.add(requester);
                if (documentID != null){
                    dbFriendList.document(documentID).update("FriendList", friendList);
                    removeFriendRequest(receiver, requester.username);
                    dbFriendList.whereEqualTo("username", requester.username).get().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            ArrayList<User> friendList2 = new ArrayList<>();
                            String documentID2 = null;
                            for (QueryDocumentSnapshot document: task1.getResult()) {
                                friendList2= document.toObject(FriendList.class).FriendList;
                                documentID2 = document.getId();
                            }
                            friendList2.add(receiver);
                            if (documentID2 != null) {
                                dbFriendList.document(documentID2).update("FriendList", friendList2);
                            } else {
                                Log.d("FBHELPER", "ADD FRIEND FAILED");
                            }
                        }
                    });
                } else {
                    Log.d("FBHELPER", "ADD FRIEND FAILED");
                }
            }
        });
        getMessageDialog(requester.username, receiver.username, messageDialog -> {
            if(messageDialog == null) {
                createMessageDialog(requester.username, receiver.username);
            } else {
                Log.d("Add friend not create Dialog", "Diaalog is present");
            }
        });
    }

    public void removeFriend(User toBeRemoved, String username) {
        CollectionReference dbFriendList = db.collection("FriendList");
        dbFriendList.whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendList = new ArrayList<>();
                ArrayList<User> updatedFriendList = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document: task.getResult()) {
                    friendList = document.toObject(FriendList.class).FriendList;
                    documentID = document.getId();
                }
                if (documentID != null){
                    for (User user : friendList) {
                        if (user.isEqual(toBeRemoved) == 0) {
                            continue;
                        }
                        updatedFriendList.add(user);
                    }
                    dbFriendList.document(documentID).update("FriendList", updatedFriendList);
                } else {
                    Log.d("FBHELPER", "REMOVE FRIEND FAILED");
                }
            }
        });
    }

    public void getFriendRequest(String username, getFriendRequestListCallback callback) {
        CollectionReference dbFriendRequest = db.collection("FriendRequests");
        dbFriendRequest.whereEqualTo("username", username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendRequestList = new ArrayList<>();
                for (QueryDocumentSnapshot document: task.getResult()) {
                    friendRequestList = document.toObject(friendRequest.class).requestListID;

                }
                callback.getFriendRequestList(friendRequestList);
            }
        });
    }

    public void getFriendList(String userID, getFriendListCallback callback) {
        CollectionReference dbFriendList = db.collection("FriendList");
        dbFriendList.whereEqualTo("userID", userID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<User> friendList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    friendList = document.toObject(FriendList.class).FriendList;
                }
                Log.d("Friend list size is ", friendList.size() + "");
                callback.getFriendList(friendList);
            }
        });
    }

    public void createMessageDialog(String username1, String username2) {
        CollectionReference dbMessageDialog = db.collection("messageDialog");
        dbMessageDialog.add(new MessageDialog(username1, username2)).addOnCompleteListener(task -> {
            Log.d("Add dialog", "Added first dialog");
            dbMessageDialog.add(new MessageDialog(username2, username1)).addOnCompleteListener(task1 -> {
                Log.d("Add dialog", "Added second dialog");
            });
        });
    }

    public void addMessagesToDialog(String currentUser, String targetUser, String message) {
        CollectionReference dbMessageDialog = db.collection("messageDialog");
        dbMessageDialog.whereEqualTo("username1", currentUser).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Message> messages = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if ((document.toObject(MessageDialog.class).username2.compareTo(targetUser)) == 0) {
                        messages = document.toObject(MessageDialog.class).messageList;
                        documentID = document.getId();
                    }
                }
                messages.add(new Message(currentUser, targetUser, message));
                if (documentID != null) {
                    dbMessageDialog.document(documentID).update("messageList", messages);
                } else {
                    Log.d("FBHELPER", "ADD MESSAGE FAILED");
                }
            }
        });
        dbMessageDialog.whereEqualTo("username1", targetUser).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Message> messages = new ArrayList<>();
                String documentID = null;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if ((document.toObject(MessageDialog.class).username2.compareTo(currentUser)) == 0) {
                        messages = document.toObject(MessageDialog.class).messageList;
                        documentID = document.getId();
                    }
                }
                messages.add(new Message(targetUser, currentUser, message));
                if (documentID != null) {
                    dbMessageDialog.document(documentID).update("messageList", messages);
                } else {
                    Log.d("FBHELPER", "ADD MESSAGE FAILED");
                }
            }
        });
    }

    public void getMessageDialog(String username1, String username2, getMessageDialogCallback callback) {
        CollectionReference dbMessageDialog = db.collection("messageDialog");
        Log.d("getMessageDialog", "username1 is " + username1 );
        dbMessageDialog.whereEqualTo("username1", username1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                MessageDialog messageDialog = null;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.toObject(MessageDialog.class).username2.compareTo(username2) == 0) {
                        messageDialog = document.toObject(MessageDialog.class);
                    }
                }
                callback.getMessageDialog(messageDialog);
            }

        });
    }
    public void friendListListener(String userID, friendListListenerCallBack callBack) {
        db.collection("FriendList").whereEqualTo("userID", userID ).addSnapshotListener((value, error) -> {
            ArrayList<User> users = new ArrayList<>();
            for(QueryDocumentSnapshot document : value) {
                users = document.toObject(FriendList.class).FriendList;
            }
            callBack.friendListListener(users);
        });
    }

    public void messageDialogListener(String username1, String username2, messageDialogListenerCallback callback) {
        db.collection("messageDialog").whereEqualTo("username1", username1).addSnapshotListener((value, error) -> {
            ArrayList<Message> messages = new ArrayList<>();
            for (QueryDocumentSnapshot document : value) {
                if (document.toObject(MessageDialog.class).username2.compareTo(username2) == 0) {
                    messages = document.toObject(MessageDialog.class).messageList;
                }
            }
            callback.messageDialogListener(messages);
        });
    }

}
