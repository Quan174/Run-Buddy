package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;

public class CreateRoutePageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    private FirebaseHelper fbHelper;
    TextView createRoutePageBackButton;
    private String userID;
    private TextView name, descrip,save;
    private MapView mapView;
    private GoogleMap map;
    private boolean isFromHistory = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_route_page);
        spHelper = new SharedPreferencesHelper(this);
        fbHelper = new FirebaseHelper(this);
        userID = spHelper.getSessionID();
        mapView = findViewById(R.id.map);

        createRoutePageBackButton = findViewById(R.id.createRoutePageBackButton);
        String format = getIntent().getStringExtra("format");
        String routeID = getIntent().getStringExtra("routeID");
        Log.d("ROUTE ID IS FROM CREATE ROUTE", routeID);
        name = findViewById(R.id.nameInputText);
        descrip = findViewById(R.id.descripInputText);
        save = findViewById(R.id.createRoutePageSaveButton);

        isFromHistory = getIntent().getBooleanExtra("isFromHistory", false);
        if(!isFromHistory){
            fbHelper.getRouteFromRouteID(spHelper.getSessionID(), "", routeID, route -> {
                mapView.setClickable(false);
                ArrayList<LatLng> myRoute = new ArrayList<>();
                for (customLatLng customLatLng : route.latLngArrayList) {
                    myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
                }
                if (mapView != null) {
                    // Initialise the MapView
                    mapView.onCreate(null);
                    // Set the map ready callback to receive the GoogleMap object
                    mapView.getMapAsync(googleMap -> {
                        ArrayList<Polyline> polylines = null;
                        MapsInitializer.initialize(this);
                        map = googleMap;
                        map.clear();
                        polylines = new ArrayList<>();
                        if (myRoute.size() >= 2) {
                            LatLng start = myRoute.get(0);
                            LatLng end = myRoute.get(myRoute.size() - 1);
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(start);
                            builder.include(end);
                            map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 70));
                            Polyline polyline1 = null;
                            Polyline polyline2 = null;
                            for (int i = 0; i < myRoute.size() - 1; i++) {
                                polyline2 = map.addPolyline(new PolylineOptions()
                                        .add(myRoute.get(i), myRoute.get(i + 1))
                                );
                                polyline1 = map.addPolyline(new PolylineOptions()
                                        .add(myRoute.get(i), myRoute.get(i + 1))
                                );
                                polyline1.setWidth(20);
                                polyline1.setColor(Color.parseColor("#FC4C02"));
                                polyline1.setEndCap(new RoundCap());
                                polyline2.setWidth(26);
                                polyline2.setColor(Color.parseColor("#FFFFFF"));
                                polyline2.setEndCap(new RoundCap());
                                polylines.add(polyline1);
                                polylines.add(polyline2);
                            }
                            Log.d("Polylines", "Added");
                            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                            for (LatLng latLng : myRoute) {
                                boundsBuilder.include(latLng);
                            }
                            LatLngBounds bounds = boundsBuilder.build();
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                            map.moveCamera(cu);
                        }
                    });
                }
            });
        } else {
            fbHelper.getRouteFromRouteID(spHelper.getSessionID(), routeID, "", route -> {
                mapView.setClickable(false);
                ArrayList<LatLng> myRoute = new ArrayList<>();
                for (customLatLng customLatLng : route.latLngArrayList) {
                    myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
                }
                if (mapView != null) {
                    // Initialise the MapView
                    mapView.onCreate(null);
                    // Set the map ready callback to receive the GoogleMap object
                    mapView.getMapAsync(googleMap -> {
                        ArrayList<Polyline> polylines = null;
                        MapsInitializer.initialize(this);
                        map = googleMap;
                        map.clear();
                        polylines = new ArrayList<>();
                        if (myRoute.size() >= 2) {
                            LatLng start = myRoute.get(0);
                            LatLng end = myRoute.get(myRoute.size() - 1);
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(start);
                            builder.include(end);
                            map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 70));
                            Polyline polyline1 = null;
                            Polyline polyline2 = null;
                            for (int i = 0; i < myRoute.size() - 1; i++) {
                                polyline2 = map.addPolyline(new PolylineOptions()
                                        .add(myRoute.get(i), myRoute.get(i + 1))
                                );
                                polyline1 = map.addPolyline(new PolylineOptions()
                                        .add(myRoute.get(i), myRoute.get(i + 1))
                                );
                                polyline1.setWidth(20);
                                polyline1.setColor(Color.parseColor("#FC4C02"));
                                polyline1.setEndCap(new RoundCap());
                                polyline2.setWidth(26);
                                polyline2.setColor(Color.parseColor("#FFFFFF"));
                                polyline2.setEndCap(new RoundCap());
                                polylines.add(polyline1);
                                polylines.add(polyline2);
                            }
                            Log.d("Polylines", "Added");
                            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                            for (LatLng latLng : myRoute) {
                                boundsBuilder.include(latLng);
                            }
                            LatLngBounds bounds = boundsBuilder.build();
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                            map.moveCamera(cu);
                        }
                    });
                }
            });
        }

        save.setOnClickListener(v -> {
            if (name.getText().toString().isEmpty() || descrip.getText().toString().isEmpty()) {
                Toast.makeText(this, "name or description cannot be emtpy", Toast.LENGTH_SHORT).show();
            } else if (!isFromHistory){
                fbHelper.saveRoute(routeID, name.getText().toString(), descrip.getText().toString());
                finish();
            } else {
                fbHelper.saveRouteByRouteID(routeID, name.getText().toString(), descrip.getText().toString());
                finish();
            }
        });

        createRoutePageBackButton.setOnClickListener(v -> finish());
    }
}