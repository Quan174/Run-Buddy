package com.example.group2_bigproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView avaUser, sendButton;
    TextView description, date, username, textView_CommentCount;
    MapView mapView;
    EditText commentField;
    RecyclerView commentRecyclerView;
    List<CommentItem> cmtItemList;
    CommentAdapter commentAdapter;
    ImageButton likeBtn, commentBtn, shareBtn, optionBtn;
    String postID;
    FirebaseHelper fbHelper;
    SharedPreferencesHelper phHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);

        backBtn = findViewById(R.id.backBtn);
        likeBtn = findViewById(R.id.btn_like);
        commentBtn = findViewById(R.id.cmtBtn);
        shareBtn = findViewById(R.id.shareBtn);
        optionBtn = findViewById(R.id.optionButton);
        avaUser = findViewById(R.id.avaUser);
        textView_CommentCount = findViewById(R.id.textView_CommentCount);
        mapView = findViewById(R.id.map);

        username = findViewById(R.id.textView_Username);
        date = findViewById(R.id.textView_Date);
        description = findViewById(R.id.textView_PostText);
        commentField = findViewById(R.id.commentField);
        sendButton = findViewById(R.id.sendButton);

        postID = getIntent().getStringExtra("postID");

        phHelper = new SharedPreferencesHelper(this);
        fbHelper = new FirebaseHelper(this);
        fbHelper.getPostByPostID(postID, postItem -> {
            username.setText(postItem.userName);
            date.setText(postItem.date);
            description.setText(postItem.description);
            textView_CommentCount.setText(postItem.comments.size()+"");
            commentAdapter = new CommentAdapter(postItem.comments, this);
            commentRecyclerView.setAdapter(commentAdapter);
            fbHelper.commentListener(postID, commentList -> {
                commentAdapter.setCommentItemList(commentList);
                commentAdapter.notifyDataSetChanged();
                textView_CommentCount.setText(commentList.size()+"");
            });
            fbHelper.getRouteFromRouteID(postItem.userID, postItem.routeID, "", route -> {
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

        });

        sendButton.setOnClickListener(v -> {
            Date currentTime = Calendar.getInstance().getTime();
            fbHelper.addComment(postID, new CommentItem(phHelper.getUsername(), commentField.getText().toString(), currentTime.toString()));
            commentField.setText("");
        });



//        PostItem postItem = getIntent().getParcelableExtra("post_item");
//        if (postItem != null){
//            username.setText(postItem.getUserName());
//            date.setText(postItem.getDate());
//            description.setText(postItem.getDescription());
//            MyUtil.setImageButtonBackground(this, postItem.getAvaUser(), avaUser);
//            postImg.setImageResource(postItem.getImageResource());
//        }


        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostActivity.this, "Clicked ava", Toast.LENGTH_SHORT).show();
                /* Navigating to author's profile */

            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(MyUtil.containsSensitiveWords(PostActivity.this, postItem.getDescription()) ){
//                    Toast.makeText(PostActivity.this, "Noi dung tuc tiu", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(PostActivity.this, "Noi dung oke", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostActivity.this, "Clicked share", Toast.LENGTH_SHORT).show();
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PostActivity.this, optionBtn);
                popupMenu.inflate(R.menu.menu_options); // Load menu resource
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if (itemId == R.id.saveRouteBtn) {
                            Toast.makeText(PostActivity.this, "Clicked save route", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.blockBtn) {
                            Toast.makeText(PostActivity.this, "Clicked block", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (itemId == R.id.reportBtn) {
                            Toast.makeText(PostActivity.this, "Clicked reported", Toast.LENGTH_SHORT).show();
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
}
