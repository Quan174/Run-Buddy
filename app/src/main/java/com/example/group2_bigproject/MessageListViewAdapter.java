package com.example.group2_bigproject;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MessageListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private ArrayList<Message> listMessage;
    private String currentUsername;
    private TextView messageContent;
    private boolean isSendByCurrentUser;


    MessageListViewAdapter(ArrayList<Message> listMessage, String currentUsername) {
        this.listMessage = listMessage;
        this.currentUsername = currentUsername;
        this.isSendByCurrentUser = false;
    }


    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listMessage
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
//        return listMessage.get(position).messageID;
        return position;
    }

    public void setListMessage(ArrayList<Message> messages) {
        this.listMessage = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewMessage = null;
            Message message = (Message) getItem(position);
        if(message.isSent){
            viewMessage = View.inflate(parent.getContext(), R.layout.sent_message_item_list_view, null);
        }
        if (!message.isSent){
            viewMessage = View.inflate(parent.getContext(), R.layout.received_message_item_list_view, null);
        }

        Log.d("is Message Sent?", "Message IsSent is " + message.isSent);
        if (message.isSent) {
            messageContent = viewMessage.findViewById(R.id.SentMessageTextView);
        }  else {
            messageContent = viewMessage.findViewById(R.id.ReceivedMessageTextview);
        }
        if (messageContent != null){
            messageContent.setText(message.message);
        }
        //Bind sữ liệu phần tử vào View



        return viewMessage;
    }
}