package com.example.group2_bigproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MessageListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<Message> listMessage;

    MessageListViewAdapter(ArrayList<Message> listMessage) {
        this.listMessage = listMessage;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewMessage;
        if (convertView == null) {
            //Sửa true thành điều kiện để check xem đây là tin nhắn đến hay đi
            if(true){
                viewMessage = View.inflate(parent.getContext(), R.layout.received_message_item_list_view, null);
            } else {
                viewMessage = View.inflate(parent.getContext(), R.layout.sent_message_item_list_view, null);
            }

        } else viewMessage = convertView;

        //Bind sữ liệu phần tử vào View
//        Message message = (Message) getItem(position);
//        ((ImageView) viewMessage.findViewById(R.id.activityHistoryMessageImage)).setText(String.format("ID = %d", message.MessageID));
//        ((TextView) viewMessage.findViewById(R.id.activityHistoryMessageName)).setText(String.format("Tên SP : %s", message.name));
//        ((ImageView) viewMessage.findViewById(R.id.idMessage)).setText(String.format("ID = %d", message.MessageID));
//        ((TextView) viewMessage.findViewById(R.id.priceMessage)).setText(String.format("Giá %d", message.price));
//        ((TextView) viewMessage.findViewById(R.id.priceMessage)).setText(String.format("Giá %d", message.price));


        return viewMessage;
    }
}