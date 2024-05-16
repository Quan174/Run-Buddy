package com.example.group2_bigproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class MapPageActivity extends FragmentActivity implements OnMapReadyCallback {
    private ConstraintLayout mapSuggestedRoutesButton;
    private ConstraintLayout mapSavedRoutesButton;
    private TextView mapSuggestedRoutesButtonText;
    private TextView mapSavedRoutesButtonText;
    private HorizontalScrollView mapSuggestedRoutesLayout;
    private HorizontalScrollView mapSavedRoutesLayout;
    private TextView menuBarHomeButton;
    private TextView menuBarRoutesButton;
    private TextView menuBarMapButton;
    private TextView menuBarSocialButton;
    private TextView menuBarProfileButton;
    private ConstraintLayout mapStartTrackingButton;
    private ImageView mapWalkingButton;
    private ImageView mapCyclingButton;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private GoogleMap map;
    private LocationRequest locationRequest;
    private Location currentLocation;
    static int FINE_PERMISSION_CODE = 1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_page);
        mapSuggestedRoutesButton = findViewById(R.id.mapSuggestedRoutesButton);
        mapSavedRoutesButton = findViewById(R.id.mapSavedRoutesButton);
        mapSuggestedRoutesButtonText = findViewById(R.id.mapSuggestedRoutesButtonText);
        mapSavedRoutesButtonText = findViewById(R.id.mapSavedRoutesButtonText);
        mapSuggestedRoutesLayout = findViewById(R.id.mapSuggestedRoutesLayout);
        mapStartTrackingButton = findViewById(R.id.mapStartTrackingButton);
        menuBarHomeButton =findViewById(R.id.menuBarHomeButton);
        menuBarRoutesButton = findViewById(R.id.menuBarRoutesButton);
        menuBarMapButton = findViewById(R.id.menuBarMapButton);
        menuBarSocialButton = findViewById(R.id.menuBarSocialButton);
        menuBarProfileButton = findViewById(R.id.menuBarProfileButton);
        mapWalkingButton = findViewById(R.id.mapWalkingButton);
        mapCyclingButton = findViewById(R.id.mapCyclingButton);

        Bundle bundle = getIntent().getExtras();
        String userID = bundle.getString("userID", "Default");

        menuBarMapButton.setTextColor(R.color.light_grey);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(MapPageActivity.this);
        locationRequest = new LocationRequest.Builder(4000).setMinUpdateDistanceMeters(10).setMinUpdateIntervalMillis(2000).setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(MapPageActivity.this);
                    }
                }
            }
        };
        getLastLocation();

        mapSuggestedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mapSuggestedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mapSuggestedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                mapSavedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                mapSavedRoutesButtonText.setTypeface(null, Typeface.NORMAL);
            }
        });

        mapSavedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mapSavedRoutesButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mapSavedRoutesButtonText.setTypeface(null, Typeface.BOLD);
                mapSuggestedRoutesButton.setBackgroundColor(Color.parseColor("#BBBBBB"));
                mapSuggestedRoutesButtonText.setTypeface(null, Typeface.NORMAL);
            }
        });

        mapStartTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, TrackingPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        menuBarHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, HomePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, RoutesPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, SocialPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        menuBarProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapPageActivity.this, ProfilePageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        mapCyclingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCyclingButton.setImageResource(R.drawable.circle_shape);
                mapWalkingButton.setImageResource(R.drawable.black_circle_shape);
            }
        });

        mapWalkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapCyclingButton.setImageResource(R.drawable.black_circle_shape);
                mapWalkingButton.setImageResource(R.drawable.circle_shape);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if (currentLocation != null){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                    .title("current location"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 20), 200, null);
        }
    }
}
