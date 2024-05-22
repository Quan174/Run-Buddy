package com.example.group2_bigproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
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

public class RouteViewActivity extends AppCompatActivity {
    private FirebaseHelper fbHelper;
    private SharedPreferencesHelper spHelper;
    String userID;
    ImageView routeViewBackButton;
    ImageView routeViewRouteImage;
    TextView routeViewRouteName;
    TextView routeViewRouteAuthor;
    TextView routeViewRouteCreatedDate;
    TextView routeViewRouteDescription;
    ImageView routeViewRouteType;
    TextView routeViewDistance;
    TextView routeViewLocation;
    TextView routeViewJoinChallengeButton;
    TextView routeViewReportButton;
    TextView routeViewSaveButton;
    TextView routeViewShareButton;
    private String routeID;
    private MapView mapView;
    private GoogleMap map;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_view_page);

        spHelper = new SharedPreferencesHelper(this);
        userID = spHelper.getSessionID();
        fbHelper = new FirebaseHelper(this);
        routeViewBackButton = findViewById(R.id.routeViewBackButton);
        mapView = findViewById(R.id.map);
        routeViewRouteName = findViewById(R.id.routeViewRouteName);
        routeViewRouteAuthor = findViewById(R.id.routeViewRouteAuthor);
        routeViewRouteCreatedDate = findViewById(R.id.routeViewRouteCreatedDate);
        routeViewRouteDescription = findViewById(R.id.routeViewRouteDescription);
        routeViewRouteType = findViewById(R.id.routeViewRouteType);
        routeViewDistance = findViewById(R.id.routeViewDistance);
        routeViewLocation = findViewById(R.id.routeViewLocation);
        routeViewJoinChallengeButton = findViewById(R.id.routeViewJoinChallengeButton);
        routeViewReportButton = findViewById(R.id.routeViewReportButton);
        routeViewSaveButton = findViewById(R.id.routeViewSaveButton);
        routeViewShareButton = findViewById(R.id.routeViewShareButton);
        routeID = getIntent().getStringExtra("routeID");
        Log.d("ROUTE ID IN ROUTE VIEW ", "ID IS " + routeID);
        fbHelper.getRouteFromRouteID(userID, routeID,"", route -> {
            routeViewRouteName.setText(route.routeName);
            routeViewRouteAuthor.setText(spHelper.getUsername());
            routeViewRouteCreatedDate.setText(route.date);
            routeViewRouteDescription.setText(route.routeDescripion);
            routeViewDistance.setText(route.length + "");
            routeViewLocation.setText(route.time);
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

        routeViewShareButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreatePostPageActivity.class);
            intent.putExtra("format", "route");
            intent.putExtra("routeID", routeID);
            startActivity(intent);
        });
        routeViewBackButton.setOnClickListener(v -> finish());
    }
}

