package com.example.group2_bigproject;

import java.util.ArrayList;

public class MessageDialog {
    public String username2;
    public String username1;
    public ArrayList<Message> messageList;
    public MessageDialog(){}
    public MessageDialog(String username1, String username2){
        this.messageList = new ArrayList<>();
        this.username1 = username1;
        this.username2 = username2;
    }
}
