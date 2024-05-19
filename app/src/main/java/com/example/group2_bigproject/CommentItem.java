package com.example.group2_bigproject;

public class CommentItem {
    private int avaUser;
    private String userName, cmtText, time;

    public CommentItem(int avaUser, String userName, String cmtText, String time){
        this.avaUser = avaUser;
        this.userName = userName;
        this.cmtText = cmtText;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public int getAvaUser() {
        return avaUser;
    }

    public String getCmtText() {
        return cmtText;
    }

    public String getTime() {
        return time;
    }
}
