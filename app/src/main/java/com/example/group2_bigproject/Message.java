package com.example.group2_bigproject;

public class Message {
    public String senderID;
    public String receiverID;
    public String message;
    public Message(){}
    public Message(String senderID, String receiverID,  String message){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
    }

}
