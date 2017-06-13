package com.graduate.gaomingzhu.planequery.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.activity.QueryOrderActivity;
import com.graduate.gaomingzhu.planequery.model.Flight;
import com.graduate.gaomingzhu.planequery.model.Orders;

import java.util.List;

/**
 * Created by GaoMingzhu on 2017/4/20.
 * email:2713940331@qq.com
 */

public class NotGoAdapter extends ArrayAdapter <Orders>{

    private  int resourceId;

    public NotGoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Orders> list) {
        super(context, resource, list);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Orders orders=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder= new ViewHolder();
            viewHolder.textView= (TextView) view.findViewById(R.id.orderNumber);
            viewHolder.textView1= (TextView) view.findViewById(R.id.orderDate);
            viewHolder.textView2= (TextView) view.findViewById(R.id.namePassenger);
            viewHolder.textView3= (TextView) view.findViewById(R.id.orderState);
            viewHolder.textView4= (TextView) view.findViewById(R.id.idNumber);
            viewHolder.textView5= (TextView) view.findViewById(R.id.fligthN);
            viewHolder.textView6= (TextView) view.findViewById(R.id.ticketP);
            viewHolder.textView7= (TextView) view.findViewById(R.id.startP);
            viewHolder.textView8= (TextView) view.findViewById(R.id.endP);
            viewHolder.textView9= (TextView) view.findViewById(R.id.seatNumber);
            viewHolder.textView10= (TextView) view.findViewById(R.id.flightNumber);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.textView.setText(orders.getObjectId());
        viewHolder.textView1.setText(orders.getDate().getDate().toString());
        viewHolder.textView2.setText(orders.getUser().getUsername());
        viewHolder.textView4.setText(orders.getUser().getIdCard());
        viewHolder.textView5.setText(orders.getFlight().getFlight());
        viewHolder.textView6.setText(orders.getFlight().getPrice()+"元");
        viewHolder.textView7.setText(orders.getFlight().getStartCity());
        viewHolder.textView8.setText(orders.getFlight().getEndCity());
        viewHolder.textView9.setText(orders.getSeat()+"号");
        viewHolder.textView3.setText("未出行");

        viewHolder.textView10.setText(orders.getFlight().getObjectId());
        return view;
    }
     class ViewHolder{
        TextView textView;
        TextView textView1;
        TextView textView2;
         TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        TextView textView7;
        TextView textView8;
         TextView  textView9;
         TextView textView10;
    }
}
