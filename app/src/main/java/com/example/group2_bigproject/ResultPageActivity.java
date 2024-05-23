package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.UUID;

public class ResultPageActivity extends AppCompatActivity {
    TextView distance;
    TextView time;
    TextView pace;
    TextView CaloriesBurned;
    ImageView resultPageHomeButton;
    TextView resultPageCreateRouteButton;
    TextView resultPageShareRouteButton;
    private SharedPreferencesHelper spHelper;
    private GoogleMap map;
    private MapView mapView;
    private FirebaseHelper fbHelper;
    private boolean isFromHistory = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        spHelper = new SharedPreferencesHelper(this);
        fbHelper = new FirebaseHelper(this);
        distance = findViewById(R.id.textView18);
        pace = findViewById(R.id.textView20);
        time = findViewById(R.id.textView21);
        CaloriesBurned = findViewById(R.id.textView22);
        resultPageShareRouteButton = findViewById(R.id.resultPageShareRouteButton);
        String routeID = getIntent().getStringExtra("routeID");
        isFromHistory = getIntent().getBooleanExtra("isFromHistory", false);
        if(!isFromHistory) {
            Double distanceTraveled = getIntent().getDoubleExtra("totalDistance", 0);
            String elapsedTime = getIntent().getStringExtra("Time");
            Long timeInMili = getIntent().getLongExtra("TimeInNumeral", 0);
            Long velocity = Math.round((distanceTraveled / 1000) / ((double) timeInMili / 1000));
            time.setText(elapsedTime.toString());
            pace.setText(velocity + "m/s");
            distance.setText(distanceTraveled.toString());
            fbHelper.getRouteFromRouteID(spHelper.getSessionID(), "",routeID, route -> {
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
            resultPageShareRouteButton.setOnClickListener(v -> {
                Intent intent = new Intent(ResultPageActivity.this, CreatePostPageActivity.class);
                intent.putExtra("format", "result");
                fbHelper.getRouteFromRouteID(spHelper.getSessionID(), "", routeID, route -> {
                    intent.putExtra("routeID", route.routeID);
                    startActivity(intent);
                });
            });
        } else {
            fbHelper.getRouteFromRouteID(spHelper.getSessionID(), routeID, "", route -> {
                time.setText(route.time);
                pace.setText("undefined");
                distance.setText(route.length+"");
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
            resultPageShareRouteButton.setOnClickListener(v -> {
                Intent intent = new Intent(ResultPageActivity.this, CreatePostPageActivity.class);
                intent.putExtra("format", "result");
                intent.putExtra("routeID", routeID);
                startActivity(intent);
            });
        }
        Log.d("ROUTE ID IS", routeID);
        Log.d("Session id is", spHelper.getSessionID());

        resultPageHomeButton = findViewById(R.id.resultPageHomeButton);
        resultPageCreateRouteButton = findViewById(R.id.resultPageCreateRouteButton);
        resultPageShareRouteButton = findViewById(R.id.resultPageShareRouteButton);
        mapView = findViewById(R.id.map);

        resultPageHomeButton.setOnClickListener(v -> finish());
        resultPageCreateRouteButton.setOnClickListener(v -> {
//            CreateRouteFragment createRouteDiaglog = new CreateRouteFragment();
//            createRouteDiaglog.show(getSupportFragmentManager(), "createroute");
            Intent intent = new Intent(ResultPageActivity.this, CreateRoutePageActivity.class);
            intent.putExtra("routeID", routeID);
            if(isFromHistory) intent.putExtra("isFromHistory", true);
            startActivity(intent);
        });
    }
}