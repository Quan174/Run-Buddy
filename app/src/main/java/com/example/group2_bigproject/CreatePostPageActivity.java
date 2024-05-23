package com.example.group2_bigproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
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
import java.util.Calendar;
import java.util.Date;

public class CreatePostPageActivity extends AppCompatActivity {
    private SharedPreferencesHelper spHelper;
    MapView mapView;
    TextView createPostPageBackButton;
    TextView createPostButton;
    ConstraintLayout createPostResultDisplay;
    ConstraintLayout createPostRouteDisplay;
    EditText contentEditText;
    TextView resultDisplayDetailText;
    TextView routeNameTextView;
    private String userID;
    FirebaseHelper fbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_page);
        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        createPostPageBackButton = findViewById(R.id.createPostPageBackButton);
        createPostButton = findViewById(R.id.createPostButton);
        createPostResultDisplay = findViewById(R.id.createPostResultDisplay);
        createPostRouteDisplay = findViewById(R.id.createPostRouteDisplay);
        contentEditText = findViewById(R.id.contentEditText);
        resultDisplayDetailText = findViewById(R.id.resultDisplayDetailText);
        routeNameTextView = findViewById(R.id.routeNameTextView);
        mapView = findViewById(R.id.map);
        String routeID = getIntent().getStringExtra("routeID");
        String format = getIntent().getStringExtra("format");
        if(format.equals("result")){
//            createPostResultDisplay.setVisibility(View.VISIBLE);

        } else {
//            createPostRouteDisplay.setVisibility(View.VISIBLE);
        }
        fbHelper = new FirebaseHelper(this);
        fbHelper.getRouteFromRouteID(spHelper.getSessionID(), routeID, "", route -> {
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
                    GoogleMap map;
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

        createPostPageBackButton.setOnClickListener(v -> finish());
        createPostButton.setOnClickListener(v -> {
            if(!contentEditText.getText().toString().isEmpty()){
                Date currentTime = Calendar.getInstance().getTime();
                fbHelper.savePost(currentTime.toString(), spHelper.getUsername(), contentEditText.getText().toString(), spHelper.getSessionID(), routeID);
                finish();
            } else {
                Toast.makeText(this, "No content", Toast.LENGTH_SHORT).show();
            }

        });
    }
}