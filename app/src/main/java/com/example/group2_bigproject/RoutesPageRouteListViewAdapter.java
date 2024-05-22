package com.example.group2_bigproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;

class RoutesPageRouteListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private ArrayList<Route> listRoute;
    private TextView activityHistoryRouteName;
    private TextView activityHistoryRouteDistance;
    private TextView activityHistoryRouteLocation;
    private MapView mapView;
    private Context mContext;
    private GoogleMap map;
    private TextView profilePageEditInformationButtom;


    RoutesPageRouteListViewAdapter(ArrayList<Route> listRoute, Context context) {
        this.listRoute = listRoute;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listRoute.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listRoute
        return listRoute.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
//        return listRoute.get(position).routeID;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewRoute;
        if (convertView == null) {
            viewRoute = View.inflate(parent.getContext(), R.layout.routes_page_routes_item_list_view, null);
        } else viewRoute = convertView;

        //Bind sữ liệu phần tử vào View
            Route route = (Route) getItem(position);
            activityHistoryRouteName = viewRoute.findViewById(R.id.activityHistoryRouteName);
            activityHistoryRouteDistance = viewRoute.findViewById(R.id.activityHistoryRouteDistance);
            activityHistoryRouteLocation = viewRoute.findViewById(R.id.activityHistoryRouteLocation);
            profilePageEditInformationButtom = viewRoute.findViewById(R.id.profilePageEditInformationButtom);

            activityHistoryRouteName.setText(route.routeName);
            activityHistoryRouteDistance.setText(route.length + " m");
            activityHistoryRouteLocation.setText(route.time);
            profilePageEditInformationButtom.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, RouteViewActivity.class);
                intent.putExtra("routeID", ((Route) getItem(position)).routeID);
                startActivity(mContext, intent, new Bundle());
            });

        ArrayList<LatLng> myRoute = new ArrayList<>();
        for (customLatLng customLatLng : route.latLngArrayList) {
            myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
        }
        mapView = viewRoute.findViewById(R.id.map);
        mapView.setClickable(false);
        if (mapView != null) {
            // Initialise the MapView
            mapView.onCreate(null);
            // Set the map ready callback to receive the GoogleMap object
            mapView.getMapAsync(googleMap -> {
                ArrayList<Polyline> polylines = null;
                MapsInitializer.initialize(mContext);
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


        return viewRoute;
    }
}