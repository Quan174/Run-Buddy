package com.example.group2_bigproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class SocialPageFriendRequestListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<User> listUser;

    SocialPageFriendRequestListViewAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
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
//        User user = (User) getItem(position);
//        ((ImageView) viewUser.findViewById(R.id.activityHistoryUserImage)).setText(String.format("ID = %d", user.UserID));
//        ((TextView) viewUser.findViewById(R.id.activityHistoryUserName)).setText(String.format("Tên SP : %s", user.name));
//        ((ImageView) viewUser.findViewById(R.id.idUser)).setText(String.format("ID = %d", user.UserID));
//        ((TextView) viewUser.findViewById(R.id.priceUser)).setText(String.format("Giá %d", user.price));
//        ((TextView) viewUser.findViewById(R.id.socialPageFriendItemButton)).setText("Accept");


        return viewUser;
    }
}