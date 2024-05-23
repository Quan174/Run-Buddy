package com.example.group2_bigproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;


public class RouteQuickViewAdapter extends RecyclerView.Adapter<RouteQuickViewAdapter.RouteQuickViewHolder> {

    private List<Route> routeList;
    Context context;

    public static class RouteQuickViewHolder extends RecyclerView.ViewHolder {
        public MapView mapView;
        public TextView routeName, details;

        public RouteQuickViewHolder(View itemView) {
            super(itemView);
            mapView = itemView.findViewById(R.id.map);
            routeName = itemView.findViewById(R.id.routeName);
            details = itemView.findViewById(R.id.routeDetailsBtn);
        }
    }

    public RouteQuickViewAdapter(List<Route> routeList, Context context) {
        this.routeList = routeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RouteQuickViewAdapter.RouteQuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_map_activity_layout, parent, false);
        return new RouteQuickViewAdapter.RouteQuickViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull RouteQuickViewAdapter.RouteQuickViewHolder holder, int position) {
        Route currentRoute = routeList.get(position);
        ArrayList<LatLng> myRoute = new ArrayList<>();
        for (customLatLng customLatLng : currentRoute.latLngArrayList) {
            myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
        }
        holder.routeName.setText(currentRoute.routeName);
        /*holder.routeImg.setText(currentItem.getCmtText());  them anh route vao day */
        if (holder.mapView != null) {
            // Initialise the MapView
            holder.mapView.onCreate(null);
            // Set the map ready callback to receive the GoogleMap object
            holder.mapView.getMapAsync(googleMap -> {
                ArrayList<Polyline> polylines = null;
                MapsInitializer.initialize(context);
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
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RouteViewActivity.class);
                intent.putExtra("routeID", currentRoute.routeID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}
