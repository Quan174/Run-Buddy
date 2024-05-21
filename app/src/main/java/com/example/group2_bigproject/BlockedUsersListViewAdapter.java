package com.example.group2_bigproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class BlockedUsersListViewAdapter extends BaseAdapter {
    final ArrayList<User> listUser;

    BlockedUsersListViewAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
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
            viewUser = View.inflate(parent.getContext(), R.layout.social_page_friend_item_view, null);
        } else viewUser = convertView;

        //Bind sữ liệu phần tử vào View
        User user = (User) getItem(position);
        ((TextView) viewUser.findViewById(R.id.socialPageFriendUsername)).setText(user.username);
        ((TextView) viewUser.findViewById(R.id.socialPageFriendItemButton)).setText("Unblock");


        return viewUser;
    }
}