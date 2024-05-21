package com.example.group2_bigproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class SearchPageUserAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private ArrayList<User> listUser;
    private FirebaseHelper fbHelper;
    private TextView username;
    private TextView addFriendButton;
    private SharedPreferencesHelper spHelper;
    private String userID;

    SearchPageUserAdapter(ArrayList<User> listUser, Context context) {
        this.spHelper = new SharedPreferencesHelper(context);
        this.listUser = listUser;
        this.fbHelper = new FirebaseHelper(context);
        this.userID = spHelper.getSessionID();
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
            viewUser = View.inflate(parent.getContext(), R.layout.social_page_friend_item_view, null);
        } else viewUser = convertView;

        //Bind sữ liệu phần tử vào View

         User user = (User) getItem(position);
         username = viewUser.findViewById(R.id.socialPageFriendUsername);
         addFriendButton = viewUser.findViewById(R.id.socialPageFriendItemButton);
         username.setText(user.username);
         addFriendButton.setText("Add Friend");
        fbHelper.getFriendRequest(user.username, users -> {
            for (User userRequested : users) {
                if (userRequested.isEqual(user) == 0) {
                    addFriendButton.setText("Remove Friend Request");
                }
            }
            fbHelper.getFriendList(userID, users1 -> {
                for (User friend : users1) {
                    if (friend.isEqual(user) == 0) {
                        addFriendButton.setText("Unfriend");
                    }
                }
                fbHelper.readUser(userID, currentUser -> {
                    addFriendButton.setOnClickListener(v -> {
                        if (addFriendButton.getText().toString().compareTo("Add Friend") == 0) {
                            fbHelper.addFriendRequest(currentUser, user.username);
                            addFriendButton.setText("Remove Friend Request");
                        } else if (addFriendButton.getText().toString().compareTo("Remove Friend Request") == 0) {
                            fbHelper.removeFriendRequest(currentUser, user.username);
                            addFriendButton.setText("Add Friend");
                        } else if (addFriendButton.getText().toString().compareTo("Unfriend") == 0) {
                            fbHelper.removeFriend(currentUser, user.username);
                            addFriendButton.setText("Add Friend");
                        }
                    });
                });
            });
        });


        return viewUser;
    }
}