package com.example.group2_bigproject;

import android.os.Parcel;
import android.os.Parcelable;

public class PostItem implements Parcelable {
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

    protected PostItem(Parcel in) {
        imageResource = in.readInt();
        avaUser = in.readInt();
        date = in.readString();
        userName = in.readString();
        description = in.readString();
    }

    public static final Creator<PostItem> CREATOR = new Creator<PostItem>() {
        @Override
        public PostItem createFromParcel(Parcel in) {
            return new PostItem(in);
        }

        @Override
        public PostItem[] newArray(int size) {
            return new PostItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeInt(avaUser);
        dest.writeString(date);
        dest.writeString(userName);
        dest.writeString(description);
    }
}