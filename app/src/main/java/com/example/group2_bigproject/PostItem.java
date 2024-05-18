package com.example.group2_bigproject;

public class PostItem {
    private int imageResource;
    private int avaUser;
    private String date;
    private String userName;
    private String description;

    public PostItem(int imageResource, int avaUser, String date, String userName, String description) {
        this.imageResource = imageResource;
        this.avaUser = avaUser;
        this.date = date;
        this.description = description;
        this.userName = userName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public int getAvaUser() {
        return avaUser;
    }
}
