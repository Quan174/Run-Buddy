package com.example.group2_bigproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class MessageDialogListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<MessageDialog> listMessageDialog;

    MessageDialogListViewAdapter(ArrayList<MessageDialog> listMessageDialog) {
        this.listMessageDialog = listMessageDialog;
    }


    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listMessageDialog.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listMessageDialog
        return listMessageDialog.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
//        return listMessageDialog.get(position).messageDialogID;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewMessageDialog;
        if (convertView == null) {
            viewMessageDialog = View.inflate(parent.getContext(), R.layout.message_dialog_item_list_view, null);
        } else viewMessageDialog = convertView;

        //Bind sữ liệu phần tử vào View
            MessageDialog messageDialog = (MessageDialog) getItem(position);
//        ((ImageView) viewMessageDialog.findViewById(R.id.activityHistoryMessageDialogImage)).setText(String.format("ID = %d", messageDialog.MessageDialogID));
//        ((TextView) viewMessageDialog.findViewById(R.id.activityHistoryMessageDialogName)).setText(String.format("Tên SP : %s", messageDialog.name));
//        ((ImageView) viewMessageDialog.findViewById(R.id.idMessageDialog)).setText(String.format("ID = %d", messageDialog.MessageDialogID));
//        ((TextView) viewMessageDialog.findViewById(R.id.priceMessageDialog)).setText(String.format("Giá %d", messageDialog.price));
//        ((TextView) viewMessageDialog.findViewById(R.id.priceMessageDialog)).setText(String.format("Giá %d", messageDialog.price));


        return viewMessageDialog;
    }
}