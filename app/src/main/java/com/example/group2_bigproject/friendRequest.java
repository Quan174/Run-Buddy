package com.example.group2_bigproject;

import java.util.ArrayList;

public class friendRequest {
    public String userID;
    public String username;
    public ArrayList<User> requestListID;

    public friendRequest(){}
    public friendRequest(String userID, String username) {
        requestListID = new ArrayList<>();
        this.userID = userID;
        this.username = username;
    }
}
