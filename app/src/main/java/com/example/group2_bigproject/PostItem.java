package com.example.group2_bigproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PostItem {

    public String date;
    public String userID;
    public String routeID;
    public ArrayList<String> likedUsers;
    public ArrayList<CommentItem> comments;
    public String userName;
    public String description;

    public PostItem(){}
    public PostItem(String date, String userName, String description, String userID, String routeID) {
        this.date = date;
        this.description = description;
        this.userName = userName;
        this.userID = userID;
        this.routeID = routeID;
        likedUsers = new ArrayList<>();
        comments = new ArrayList<>();
    }
}