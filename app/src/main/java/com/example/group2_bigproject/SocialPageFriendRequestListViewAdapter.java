package com.example.group2_bigproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class SocialPageFriendRequestListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private ArrayList<User> listUser;
    private FirebaseHelper fbHelper;
    private SharedPreferencesHelper spHelper;
    private TextView accept;
    private TextView deny;
    private String userID;
    private TextView username;


    SocialPageFriendRequestListViewAdapter(ArrayList<User> listUser, Context context) {
        this.listUser = listUser;
        this.fbHelper = new FirebaseHelper(context);
        this.spHelper = new SharedPreferencesHelper(context);
        userID = spHelper.getSessionID();
    }


    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listUser
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
//        return listUser.get(position).userID;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewUser;
        if (convertView == null) {
            viewUser = View.inflate(parent.getContext(), R.layout.social_page_friend_request_item_view, null);
        } else viewUser = convertView;

        //Bind sữ liệu phần tử vào View
            User user = (User) getItem(position);
            username = viewUser.findViewById(R.id.socialPageFriendRequestUsername);
            username.setText(user.username);
            accept = viewUser.findViewById(R.id.socialPageFriendRequestItemAcceptButton);
            deny = viewUser.findViewById(R.id.socialPageFriendRequestItemDenyButton);
            fbHelper.readUser(userID, currentUser -> {
                accept.setOnClickListener(v -> {
                    fbHelper.addFriend(currentUser, user);
                    listUser.remove(position);
                    notifyDataSetChanged();
                });
                deny.setOnClickListener(v -> {
                    fbHelper.removeFriendRequest(currentUser, user.username);
                    listUser.remove(position);
                    notifyDataSetChanged();
                });
            });

//        ((ImageView) viewUser.findViewById(R.id.activityHistoryUserImage)).setText(String.format("ID = %d", user.UserID));
//        ((TextView) viewUser.findViewById(R.id.activityHistoryUserName)).setText(String.format("Tên SP : %s", user.name));
//        ((ImageView) viewUser.findViewById(R.id.idUser)).setText(String.format("ID = %d", user.UserID));
//        ((TextView) viewUser.findViewById(R.id.priceUser)).setText(String.format("Giá %d", user.price));
//        ((TextView) viewUser.findViewById(R.id.socialPageFriendItemButton)).setText("Accept");


        return viewUser;
    }
}