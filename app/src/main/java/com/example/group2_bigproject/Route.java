package com.example.group2_bigproject;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Route {
    public ArrayList<LatLng> latLngArrayList;
    public int length;

    public Route() {}
    public Route(ArrayList<LatLng> latLngArrayList, int length) {
        this.latLngArrayList = latLngArrayList;
        this.length = length;
    }
}
