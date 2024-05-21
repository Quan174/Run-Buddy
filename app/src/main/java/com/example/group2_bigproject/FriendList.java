package com.example.group2_bigproject;

import java.util.ArrayList;

public class FriendList {
    public ArrayList<User>  FriendList;
    public String userID;
    public String username;

    public FriendList(){};
    public FriendList(String userID, String username){
        this.userID = userID;
        FriendList = new ArrayList<>();
        this.username = username;
    }
}
