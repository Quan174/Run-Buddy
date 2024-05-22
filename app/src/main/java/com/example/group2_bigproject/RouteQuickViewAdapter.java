package com.example.group2_bigproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RouteQuickViewAdapter extends RecyclerView.Adapter<RouteQuickViewAdapter.RouteQuickViewHolder> {

    private List<Route> routeList;

    public static class RouteQuickViewHolder extends RecyclerView.ViewHolder {
        public ImageView routeImg;
        public TextView routeName, details;

        public RouteQuickViewHolder(View itemView) {
            super(itemView);
            routeImg = itemView.findViewById(R.id.routeImg);
            routeName = itemView.findViewById(R.id.routeName);
            details = itemView.findViewById(R.id.routeDetailsBtn);
        }
    }

    public RouteQuickViewAdapter(List<Route> routeList) {
        this.routeList = routeList;
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
        holder.routeName.setText(currentRoute.routeName);
        /*holder.routeImg.setText(currentItem.getCmtText());  them anh route vao day */

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Clicked route details", Toast.LENGTH_SHORT).show();
                /* Navigating to route details */

            }
        });
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}
