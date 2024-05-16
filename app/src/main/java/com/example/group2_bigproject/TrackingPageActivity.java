package com.example.group2_bigproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class TrackingPageActivity extends FragmentActivity implements OnMapReadyCallback {
    private dBHelper helper;
    private SQLiteDatabase db;
    private TextView velocityTrack;
    private TextView distance;
    private TextView trackingPageBackButton;
    private ConstraintLayout trackingPagePauseButton;
    private TextView trackingPagePauseButtonText;
    private TextView trackingPageFinishButton;
    private Chronometer chronometer;
    private long lastPause;
    static int FINE_PERMISSION_CODE = 1;
    private LocationRequest locationRequest;

    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private double distanceMoved;
    private long lastRecordedTime;
    private GoogleMap map;
    private ArrayList<LatLng> myRoute;
    private List<Polyline> polylines = null;

    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_page);
        helper = new dBHelper(this);
        db = helper.getWritableDatabase();
        lastRecordedTime = 0;
        distance = findViewById(R.id.textView13);
        trackingPageBackButton = findViewById(R.id.trackingPageBackButton);
        trackingPagePauseButton = findViewById(R.id.trackingPagePauseButton);
        trackingPagePauseButtonText = findViewById(R.id.trackingPagePauseButtonText);
        trackingPageFinishButton = findViewById(R.id.trackingPageFinishButton);
        chronometer = findViewById(R.id.simpleChronometer);
        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");
        chronometer.start();
        trackingPageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        trackingPagePauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackingPagePauseButtonText.getText()=="Pause") {
                    trackingPagePauseButtonText.setText("Continue");
                    started = false;
                    lastPause = chronometer.getBase() - SystemClock.elapsedRealtime();
                    chronometer.stop();
                    trackingPageFinishButton.setVisibility(View.VISIBLE);
                } else {
                    trackingPagePauseButtonText.setText("Pause");
                    chronometer.setBase(SystemClock.elapsedRealtime() + lastPause);
                    chronometer.start();
                    trackingPageFinishButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        trackingPageFinishButton.setOnClickListener(v -> {
            Intent intent = new Intent(TrackingPageActivity.this, ResultPageActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("userID", "000");
            intent.putExtras(bundle2);
            startActivity(intent);
            finish();
        });

        polylines = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        started = true;
        myRoute = new ArrayList<>();
        distanceMoved = 0;
        locationRequest = new LocationRequest.Builder(4000).setMinUpdateDistanceMeters(10).setMinUpdateIntervalMillis(2000).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    distanceMoved += currentLocation.distanceTo(location);
                    currentLocation = location;
                    double timeSpend = (double) (showElapsedTime() - lastRecordedTime);
                    timeSpend/=1000;
                    lastRecordedTime = showElapsedTime();
                    double distanceInKilometres = (double) Math.round(distanceMoved) /1000;
                    distanceInKilometres = (double) Math.round(distanceInKilometres * 100) /100;
                    double velocity = distanceInKilometres*1000/timeSpend;
                    myRoute.add(new LatLng(location.getLatitude(), location.getLongitude()));
                    distance.setText(Double.toString(distanceInKilometres));

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(TrackingPageActivity.this);
                }
            }
        };
        getLastLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started) {
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                Log.d("successful updated location", "location is ");
                currentLocation = location;
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("Starting location"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location is denied, please allow permission", Toast.LENGTH_LONG   ).show();
            }

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.clear();
        if (polylines != null) {
            polylines.clear();
        }
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
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(start.latitude, start.longitude))
                    .title("Starting location"));
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(end.latitude, end.longitude))
                    .title("Ending location"));
            Log.d("Polylines", "Added");
        }

    }

    private long showElapsedTime() {
        return SystemClock.elapsedRealtime() - chronometer.getBase();
    }
}