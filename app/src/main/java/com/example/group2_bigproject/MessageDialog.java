package com.example.group2_bigproject;

import java.util.ArrayList;

public class MessageDialog {
    public String userID1;
    public String userID2;
    public ArrayList<Message> messageList;
    public MessageDialog(){}
    public MessageDialog(String userID1, String userID2, ArrayList<Message> messageList){
        this.messageList = messageList;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }
}
