package com.example.group2_bigproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class RoutesPageRouteListViewAdapter extends BaseAdapter {

    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    final ArrayList<Route> listRoute;

    RoutesPageRouteListViewAdapter(ArrayList<Route> listRoute) {
        this.listRoute = listRoute;
    }

    @Override
    public int getCount() {
        //Trả về tổng số phần tử, nó được gọi bởi ListView
        return listRoute.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listRoute
        return listRoute.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
//        return listRoute.get(position).routeID;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới

        View viewRoute;
        if (convertView == null) {
            viewRoute = View.inflate(parent.getContext(), R.layout.activity_history_item_list_view, null);
        } else viewRoute = convertView;

        //Bind sữ liệu phần tử vào View
//        Route route = (Route) getItem(position);
//        ((ImageView) viewRoute.findViewById(R.id.activityHistoryRouteImage)).setText(String.format("ID = %d", route.RouteID));
//        ((TextView) viewRoute.findViewById(R.id.activityHistoryRouteName)).setText(String.format("Tên SP : %s", route.name));
//        ((ImageView) viewRoute.findViewById(R.id.idRoute)).setText(String.format("ID = %d", route.RouteID));
//        ((TextView) viewRoute.findViewById(R.id.priceRoute)).setText(String.format("Giá %d", route.price));
//        ((TextView) viewRoute.findViewById(R.id.priceRoute)).setText(String.format("Giá %d", route.price));


        return viewRoute;
    }
}