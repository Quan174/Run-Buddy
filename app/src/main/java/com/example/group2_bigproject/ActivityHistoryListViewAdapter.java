package com.example.group2_bigproject;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

class ActivityHistoryListViewAdapter extends BaseAdapter implements OnMapReadyCallback {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private Context mContext;
    private ArrayList<Route> listRoute;
    private MapView mapView;
    private TextView date;
    private TextView distance;
    private TextView runTime;
    private GoogleMap map;
    Button button;

    ActivityHistoryListViewAdapter(ArrayList<Route> listRoute, Context context) {
        this.listRoute = listRoute;
        mContext = context;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewRoute;
        if (convertView == null) {
            viewRoute = View.inflate(parent.getContext(), R.layout.activity_history_item_list_view, null);
        } else viewRoute = convertView;


        //Bind sữ liệu phần tử vào View
        Route route = (Route) getItem(position);
        ArrayList<LatLng> myRoute = new ArrayList<>();
        for (customLatLng customLatLng : route.latLngArrayList) {
            myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
        }
        button = viewRoute.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ResultPageActivity.class);
                intent.putExtra("runHistoryID", route.routeID);
                mContext.startActivity(intent);
            }
        });
        mapView = viewRoute.findViewById(R.id.map);
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
        date = viewRoute.findViewById(R.id.RouteHistoryDate);
        distance = viewRoute.findViewById(R.id.activityHistoryRouteDistance);
        runTime = viewRoute.findViewById(R.id.activityHistoryRouteTime);
        date.setText(route.date);
        distance.setText(route.length + " m");
        runTime.setText(route.time);
        return viewRoute;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}