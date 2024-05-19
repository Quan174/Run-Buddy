package com.example.group2_bigproject;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class Route {
    public String routeID;
    public String routeName;
    public String routeDescripion;
    public String userID;
    public ArrayList<LatLng> latLngArrayList;
    public int length;
    public String time;

    public Route() {}
    public Route(String userID, ArrayList<LatLng> latLngArrayList, int length, String time) {
        this.routeID = UUID.randomUUID().toString();
        this.latLngArrayList = latLngArrayList;
        this.length = length;
        this.userID = userID;
        this.time = time;
        this.routeName = "UNSAVED";
        this.routeDescripion = "UNSAVED";
    }
}
