package com.example.group2_bigproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesHelper {
    // Sharedpref file name
    private static final String PREF_NAME = "session";
    //Initialising the SharedPreferences
    SharedPreferences pref;
    private Context context;
    public SharedPreferencesHelper(Context context) {
        this.context = context;
        pref = (this.context)
                .getSharedPreferences(PREF_NAME, 0);
    }

    public void startSession(String userID, String username) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userID", userID);
        editor.putString("username", username);
        Log.d("START SESSION", "USERNAME IS " + username);
        editor.apply();
    }

    public String getSessionID() {
        return pref.getString("userID", null);
    }
    public String getUsername() {return pref.getString("username", null);}

    public void endSession() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
