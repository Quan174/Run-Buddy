package com.example.group2_bigproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class dBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mobileProject";
    private static final int DATABASE_VERSION = 1;

    public dBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE users (\n" +
                "  userID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  email VARCHAR(255) UNIQUE NOT NULL,\n" +
                "  username VARCHAR(255) UNIQUE NOT NULL,\n" +
                "  password VARCHAR(255) NOT NULL,\n" +
                "  created_at DATE NOT NULL\n" +
                ");";

        String createDetailUserTable = "CREATE TABLE userProfile (\n" +
                "  userID INTEGER PRIMARY KEY REFERENCES users(userID),\n" +
                "  avatar VARCHAR,  -- Store the directory of the avatar\n" +
                "  displayName VARCHAR,\n" +
                "  lastName VARCHAR,\n" +
                "  firstName VARCHAR,\n" +
                "  gender VARCHAR,\n" +
                "  birthday DATE,\n" +
                "  phoneNumber VARCHAR,\n" +
                "  address VARCHAR,\n" +
                "  height INTEGER,\n" +
                "  weight INTEGER,\n" +
                "  number_of_friends INTEGER\n" +
                ");";

        String createFriendTable = "CREATE TABLE friends (\n" +
                "  userID1 INTEGER NOT NULL REFERENCES users(userID),\n" +
                "  userID2 INTEGER NOT NULL REFERENCES users(userID),\n" +
                "  added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- Added default timestamp\n" +
                ");";

        String createFriendRequestTable = "CREATE TABLE friendRequest (\n" +
                "  senderID INTEGER NOT NULL REFERENCES users(userID),\n" +
                "  receiverID INTEGER NOT NULL REFERENCES users(userID)\n" +
                ");";

        String createRouteTable = "CREATE TABLE routes (\n" +
                "  routeID VARCHAR PRIMARY KEY,\n" +
                "  userID INTEGER REFERENCES users(userID) NOT NULL,\n" +
                "  routeName VARCHAR,\n" +
                "  routeDescription TEXT,\n" +
                "  length INTEGER,\n" +
                "  rating TINYINT\n" +
                ");";

        String createDetailRouteTable = "CREATE TABLE routeDetail (\n" +
                "  routeID VARCHAR,\n" +
                "  numeric INTEGER,\n" +
                "  lat INTEGER,\n" +
                "  lon INTEGER,\n" +
                "  FOREIGN KEY (routeID) REFERENCES routes(routeID)  -- Added foreign key constraint\n" +
                ");";

        String createPostTable = "CREATE TABLE posts (\n" +
                "  postID INTEGER PRIMARY KEY AUTOINCREMENT,  -- Auto-incrementing primary key\n" +
                "  routeID INTEGER REFERENCES route(routeID),  -- Foreign key to route table\n" +
                "  userID INTEGER NOT NULL REFERENCES users(userID),  -- Foreign key to users table\n" +
                "  title VARCHAR,\n" +
                "  body TEXT,  -- Note: 'Content of the post'\n" +
                "  status VARCHAR,\n" +
                "  type INTEGER,\n" +
                "  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- Added default timestamp\n" +
                ");";

        String createPostReactionTable = "CREATE TABLE postReaction (\n" +
                "  postID INTEGER NOT NULL REFERENCES posts(postID),  -- Foreign key to posts table\n" +
                "  userID INTEGER NOT NULL REFERENCES users(userID),  -- Foreign key to users table\n" +
                "  reactionType INTEGER\n" +
                ");\n";
        String createPostCommentTable = "CREATE TABLE postsComment (\n" +
                "  postID INTEGER NOT NULL REFERENCES posts(postID),  -- Foreign key to posts table\n" +
                "  userID INTEGER NOT NULL REFERENCES users(userID),  -- Foreign key to users table\n" +
                "  content TEXT\n" +
                ");\n";
        String createRunHistoryTable = "CREATE TABLE runHistory (\n" +
                "  routeID VARCHAR PRIMARY KEY,\n" +
                "  userID INTEGER NOT NULL REFERENCES users(user_id),  -- Foreign key to users table\n" +
                "  length INTEGER,\n" +
                "  duration INTEGER,\n" +
                "  time_ran DATE NOT NULL\n" +
                ");\n";
        String createRunHistoryDetailTable = "CREATE TABLE runHistoryDetail (\n" +
                "  routeID VARCHAR REFERENCES runHistory(routeID),  -- Foreign key to runHistory table\n" +
                "  numeric INTEGER,\n" +
                "  lat INTEGER,\n" +
                "  lon INTEGER\n" +
                ");\n";

        db.execSQL(createUserTable);
        db.execSQL(createDetailUserTable);
        db.execSQL(createFriendTable);
        db.execSQL(createFriendRequestTable);
        db.execSQL(createDetailRouteTable);
        db.execSQL(createRouteTable);
        db.execSQL(createPostTable);
        db.execSQL(createPostReactionTable);
        db.execSQL(createPostCommentTable);
        db.execSQL(createRunHistoryTable);
        db.execSQL(createRunHistoryDetailTable);
    }

    /**
     This method add an user to the database!, accept string only.
     */
    public void addUser(String username, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO users (routeID, userID, password, created_at) VALUES (?,?,?,?)",
                new String[]{email, username, password, String.valueOf(new Date(System.currentTimeMillis()))});
    }

    /**
     * This method is used  to check an user password. return an integer
     * 0 means the password matched.
     * 1 means the password is wrong.
     * 2 means there is no matching username
     */
    public int checkPassword(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("users", null, "username" + " = ?", new String[] { username },null, null, null);
        if (cursor.getCount() == 0) {
            return 2;
        }
        cursor.moveToFirst();
        String passwordInDb = cursor.getString(3);
        if (passwordInDb.compareTo(password) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * This method save a route to the database and return the unique ID of that route.
     */
    public String addRoute(String userID, String routeName, String routeDescription, Integer length, Integer rating) {
        String random = UUID.randomUUID().toString();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO routes (routeID,userID, routeName, routeDescription, length, rating) VALUES (?,?,?,?,?,?)",
                new String[]{random ,userID, routeName, routeDescription,length.toString() ,rating.toString() });
        return random;
    }

    public String addRouteToHistory(String userID, Integer length, Integer duration) {
        String random = UUID.randomUUID().toString();
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO runHistory (routeID,userID, length, duration, time_ran) VALUES (?,?,?,?,?)",
                new String[]{random ,userID, String.valueOf(length), String.valueOf(duration),String.valueOf(new Date(System.currentTimeMillis()))});
        return random;
    }

    public void addRouteDetailToHistory(ArrayList<LatLng> myRoute, String UUID) {
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;
        for (LatLng location : myRoute) {
            db.execSQL("INSERT INTO runHistoryDetail (routeID, numeric, lat, lon) VALUES (?,?,?,?)",
                    new String[]{UUID , Integer.toString(count), Double.toString(location.latitude), Double.toString(location.longitude) });
            count++;
        }
    }

    public void addRouteDetail(ArrayList<LatLng> myRoute, String UUID) {
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;
        for (LatLng location : myRoute) {
            db.execSQL("INSERT INTO routeDetail (routeID, numeric, lat, lon) VALUES (?,?,?,?)",
                    new String[]{UUID , Integer.toString(count), Double.toString(location.latitude), Double.toString(location.longitude) });
            count++;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_user_table = "DROP TABLE IF EXISTS users";
        String drop_route_table= "DROP TABLE IF EXISTS routes";
        db.execSQL(drop_user_table);
        db.execSQL(drop_route_table);

        onCreate(db);
    }
}
