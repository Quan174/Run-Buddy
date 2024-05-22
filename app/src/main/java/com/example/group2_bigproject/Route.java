package com.example.group2_bigproject;

import android.os.Build;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Route {
    public String routeID;
    public String routeName;
    public String routeDescripion;
    public String userID;
    public ArrayList<customLatLng> latLngArrayList;
    public int length;
    public String time;
    public String date;

    public Route(){}
    public Route(String routeName){
        this.routeName = routeName;
    }
    public Route(String userID, ArrayList<LatLng> latLngArrayList, int length, String time) {
        this.latLngArrayList = new ArrayList<>();
        for(LatLng latLng : latLngArrayList) {
            this.latLngArrayList.add(new customLatLng(latLng.latitude, latLng.longitude));
        }
        this.routeID = UUID.randomUUID().toString();
        this.length = length;
        this.userID = userID;
        this.time = time;
        this.routeName = "UNSAVED";
        this.routeDescripion = "UNSAVED";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter  dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            this.date = dtf.format(now);
        } else {
            this.date = "unSpecify";
        }
    }
}
