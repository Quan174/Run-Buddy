package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

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
    public void saveRouteToRouteHistory(Route route, saveRouteToRunHistoryCallback callback){
        CollectionReference dbRunHistory = db.collection("routeHistory");
        dbRunHistory.add(route).addOnSuccessListener(documentReference -> {
            callback.saveRouteToRunHistory(documentReference.getId());
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
                    if (document.toObject(Route.class).userID.compareTo(userID) == 0) {
                        Log.d("ADDED ROUTE", document.getId());
                        routes.add(document.toObject(Route.class));
                    }
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
                messages.add(new Message(true, message));
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
                messages.add(new Message(false, message));
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
                    break;
                }
            }
            callback.messageDialogListener(messages);
        });
    }

    public void getAllMessageDialog(String username, getAllMessageDialogCallback callback) {
        db.collection("messageDialog").get().addOnCompleteListener(task -> {
            ArrayList<MessageDialog> messageDialogArrayList = null;
            if (task.isSuccessful()) {
                messageDialogArrayList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.toObject(MessageDialog.class).username1.compareTo(username) != 0) {
                        continue;
                    }
                    messageDialogArrayList.add(document.toObject(MessageDialog.class));
                }
            }
            callback.getAllMessageDialog(messageDialogArrayList);
        });
    }

    public void getAllMessageDialogListener(String username, getAllMessageDialogListenerCallback callback) {
        db.collection("messageDialog").addSnapshotListener((value, error) -> {
           ArrayList<MessageDialog> messageDialogArrayList = new ArrayList<>();
           for (QueryDocumentSnapshot document : value) {
               if (document.toObject(MessageDialog.class).username1.compareTo(username) != 0) {
                   continue;
               }
               messageDialogArrayList.add(document.toObject(MessageDialog.class));
           }
           callback.getAllMessageDialog(messageDialogArrayList);
        });
    }

    public void saveRoute(String routeID, String name, String description) {
        CollectionReference dbRoutes = db.collection("routeHistory");
        dbRoutes.document(routeID).update("routeName", name, "routeDescripion", description).addOnCompleteListener(task -> {
            Log.d("saveRoute", "Route saved to routes");
        });
    }

    public void searchAllSavedRoute(String userID, RouteReader callback) {
        CollectionReference dbRoutes = db.collection("routeHistory");
        dbRoutes.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Route> routeArrayList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.toObject(Route.class).userID.compareTo(userID) == 0
                            && document.toObject(Route.class).routeName.compareTo("UNSAVED") != 0 ) {
                        routeArrayList.add(document.toObject(Route.class));
                    }
                }
                callback.readRoute(routeArrayList);
            }
        });
    }

    public void getRouteFromRouteID(String userID, String routeID, String documentID, getRouteFromRouteIDCallback callback) {
        if (documentID.compareTo("") == 0) {
            CollectionReference dbRoutes = db.collection("routeHistory");
            dbRoutes.whereEqualTo("routeID", routeID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Route route = new Route();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (userID.compareTo(document.toObject(Route.class).userID) == 0) {
                            route = document.toObject(Route.class);
                        }
                    }
                    callback.getRouteFromRouteID(route);
                }
            });
        } else {
            CollectionReference dbRoutes = db.collection("routeHistory");
            dbRoutes.document(documentID).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Route route = document.toObject(Route.class);
                    callback.getRouteFromRouteID(route);
                }
            });
        }
    }

    public void saveRouteByRouteID(String routeID, String name, String description) {
        CollectionReference dbRoutes = db.collection("routeHistory");
        dbRoutes.whereEqualTo("routeID", routeID).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String documentID = null;
                for(QueryDocumentSnapshot document: task.getResult()){
                    documentID = document.getId();
                }
                saveRoute(documentID, name, description);
            }
        });
    }

    public void savePost(String date, String userName, String description, String userID, String routeID){
        CollectionReference dbPost = db.collection("postList");
        dbPost.add(new PostItem(date, userName, description, userID, routeID)).addOnCompleteListener(task -> {
           Log.d("ADDPOST", "post added");
        });
    }

    public void getPost(String userID, String userName, getPostCallBack callBack){
        getFriendList(userID, users -> {
            HashSet<String> userNames = new HashSet<>();
            for(User user : users){
                userNames.add(user.username);
            }
            userNames.add(userName);
            CollectionReference dbPost = db.collection("postList");
            dbPost.get().addOnCompleteListener(task -> {
                ArrayList<PostItem> posts = new ArrayList<>();
               if(task.isSuccessful()){
                   for (QueryDocumentSnapshot document: task.getResult()){
                       if (userNames.contains(document.toObject(PostItem.class).userName)){
                           posts.add(document.toObject(PostItem.class));
                       }
                   }
                   callBack.getPost(posts);
               }
            });
        });
    }

    public void getPostIDByPost(PostItem postItem, getPostIDByPostCallBack callBack){
        CollectionReference dbPost = db.collection("postList");
        dbPost.whereEqualTo("userID", postItem.userID).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               String documentID = null;
               for (QueryDocumentSnapshot document : task.getResult()){
                   PostItem temp = document.toObject(PostItem.class);
                   if(temp.date.compareTo(postItem.date)+temp.description.compareTo(postItem.description)+temp.routeID.compareTo(postItem.routeID)==0){
                       documentID = document.getId();
                   }
               }
               callBack.getPostIDByPost(documentID);
           }
        });
    }

    public void getPostByPostID(String postID, getPostByPostID callBack){
        CollectionReference dbPost = db.collection("postList");
        dbPost.document(postID).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               callBack.getPostByPostID(task.getResult().toObject(PostItem.class));
           }
        });
    }

    public void addComment(String postID, CommentItem comment){
        getPostByPostID(postID, postItem -> {
            ArrayList<CommentItem> commentItems = new ArrayList<>();
            commentItems = postItem.comments;
            commentItems.add(comment);
            db.collection("postList").document(postID).update("comments", commentItems);
        });
    }

    public void postListener(String userID, String userName, postListenerCallBack callBack){
        getFriendList(userID, users -> {
            HashSet<String> userNames = new HashSet<>();
            for(User user : users){
                userNames.add(user.username);
            }
            userNames.add(userName);
            db.collection("postList").addSnapshotListener((value, error) -> {
                ArrayList<PostItem> postList = new ArrayList<>();
                for(QueryDocumentSnapshot document: value){
                    if(userNames.contains(document.toObject(PostItem.class).userName)){
                        postList.add(document.toObject(PostItem.class));
                    }
                }
                callBack.postListener(postList);
            });
        });
    }

    public void commentListener(String postID, commentListenerCallBack callBack){
        db.collection("postList").document(postID).addSnapshotListener((value, error) -> {
           callBack.commentListener(value.toObject(PostItem.class).comments);
        });
    }

    public void pressHeart(String postID, String userID){
        getPostByPostID(postID, postItem -> {
            boolean isLiked = false;
            for(String userid : postItem.likedUsers){
                if(userID.compareTo(userid)==0){
                    isLiked = true;
                }
            }
            if(isLiked){
                ArrayList<String> modifyLikedUsersID = new ArrayList<>();
                for(String userid : postItem.likedUsers){
                    if(userID.compareTo(userid)==0){
                        continue;
                    }
                    modifyLikedUsersID.add(userid);
                }
                db.collection("postList").document(postID).update("likedUsers", modifyLikedUsersID);
            } else {
                ArrayList<String> modifyLikedUsersID = postItem.likedUsers;
                modifyLikedUsersID.add(userID);
                db.collection("postList").document(postID).update("likedUsers", modifyLikedUsersID);
            }
        });
    }

    public void isLiked(String postID, String userID, isLikedCallBack callBack){
        db.collection("postList").document(postID).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               Boolean isLiked = false;
               for(String userid : task.getResult().toObject(PostItem.class).likedUsers){
                   if(userID.compareTo(userid)==0){
                       isLiked = true;
                   }
               }
               callBack.isLiked(isLiked);
           }
        });
    }

    public void isLikedListener(String postID, String userID, isLikedListenerCallBack callBack){
        db.collection("postList").document(postID).addSnapshotListener((value, error) -> {
            callBack.isLikedListener();
        });
    }

    public void searchRouteByName(String name, searchRouteByNameCallback callback) {
        db.collection("routeHistory").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Route> routes = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    if (document.toObject(Route.class).routeName.contains(name)) {
                        routes.add(document.toObject(Route.class));
                    }
                }
                callback.searchRouteByName(routes);
            }
        });
    }
}
