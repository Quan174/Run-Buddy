package com.example.group2_bigproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;

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

public class Adapter extends RecyclerView.Adapter<Adapter.PostViewHolder> {
    private List<PostItem> postList;
    public SharedPreferencesHelper pfHelper;
    public FirebaseHelper fbHelper;
    public Context context;

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public ImageButton likeBtn, commentBtn, shareBtn, optionBtn, btn_liked;
        public MapView mapView;
        public TextView postDate, textView_CommentCount;
        public TextView userName;
        public TextView postDescription;
        public ImageView avaUser;

        public PostViewHolder(View itemView) {
            super(itemView);
            mapView = itemView.findViewById(R.id.map);
            avaUser = itemView.findViewById(R.id.avaUser);
            likeBtn = itemView.findViewById(R.id.btn_like);
            commentBtn = itemView.findViewById(R.id.cmtBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            optionBtn = itemView.findViewById(R.id.optionButton);
            userName = itemView.findViewById(R.id.textView_Username);
            postDescription = itemView.findViewById(R.id.textView_PostText);
            postDate = itemView.findViewById(R.id.textView_Date);
            textView_CommentCount = itemView.findViewById(R.id.textView_CommentCount);
            btn_liked = itemView.findViewById(R.id.btn_liked);
        }
    }

    public Adapter(List<PostItem> postList, Context context) {
        this.postList = postList;
        this.context = context;
        pfHelper = new SharedPreferencesHelper(context);
        fbHelper = new FirebaseHelper(context);
    }

    public void updatePostList(ArrayList<PostItem> postList){
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostItem currentItem = postList.get(position);
        holder.postDate.setText(currentItem.date);
        holder.postDescription.setText(currentItem.description);
        holder.userName.setText(currentItem.userName);
        holder.textView_CommentCount.setText(currentItem.comments.size()+"");

        fbHelper.getPostIDByPost(currentItem, postID -> {
//            fbHelper.isLikedListener(postID, pfHelper.getSessionID(), () -> {
//                notifyDataSetChanged();
//            });
            fbHelper.isLiked(postID, pfHelper.getSessionID(), isLiked -> {
                if(isLiked){
                    holder.likeBtn.setVisibility(View.INVISIBLE);
                    holder.btn_liked.setVisibility(View.VISIBLE);
                } else {
                    holder.likeBtn.setVisibility(View.VISIBLE);
                    holder.btn_liked.setVisibility(View.INVISIBLE);
                }
                holder.likeBtn.setOnClickListener(v -> {
                    if(isLiked){
                        holder.likeBtn.setVisibility(View.VISIBLE);
                        holder.btn_liked.setVisibility(View.INVISIBLE);
                    } else {
                        holder.likeBtn.setVisibility(View.INVISIBLE);
                        holder.btn_liked.setVisibility(View.VISIBLE);
                    }
                    fbHelper.pressHeart(postID, pfHelper.getSessionID());
                });
                holder.btn_liked.setOnClickListener(v -> {
                    if(isLiked){
                        holder.likeBtn.setVisibility(View.VISIBLE);
                        holder.btn_liked.setVisibility(View.INVISIBLE);
                    } else {
                        holder.likeBtn.setVisibility(View.INVISIBLE);
                        holder.btn_liked.setVisibility(View.VISIBLE);
                    }
                    fbHelper.pressHeart(postID, pfHelper.getSessionID());
                });
            });
        });


        fbHelper.getRouteFromRouteID(currentItem.userID, currentItem.routeID, "", route -> {
            if(route!=null){
                ArrayList<LatLng> myRoute = new ArrayList<>();
                for (customLatLng customLatLng : route.latLngArrayList) {
                    myRoute.add(new LatLng(customLatLng.latitude, customLatLng.longitude));
                }
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
            }
        });

        holder.avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewProfilePageActivity.class);
                context.startActivity(intent);
            }
        });

        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PostActivity.class);
                fbHelper.getPostIDByPost(currentItem, postID -> {
                    intent.putExtra("postID", postID);
                    holder.itemView.getContext().startActivity(intent);
                });
            }
        });

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Clicked like", Toast.LENGTH_SHORT).show();
            }
        });

//        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(holder.itemView.getContext(), "Clicked share", Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.optionBtn);
                popupMenu.inflate(R.menu.menu_options); // Load menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.saveRouteBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked save route", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.blockBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked block", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.reportBtn) {
                            Toast.makeText(holder.itemView.getContext(), "Clicked report", Toast.LENGTH_SHORT).show();

                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

