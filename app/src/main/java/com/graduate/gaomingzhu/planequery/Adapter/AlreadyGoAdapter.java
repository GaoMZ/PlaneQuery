package com.graduate.gaomingzhu.planequery.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.Orders;

import java.util.List;

/**
 * Created by GaoMingzhu on 2017/4/22.
 * email:2713940331@qq.com
 */

public class AlreadyGoAdapter extends ArrayAdapter<Orders> {

    private  int resourceId;

    public AlreadyGoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Orders> list) {
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
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder= new ViewHolder();
            viewHolder.textView= (TextView) view.findViewById(R.id.orderNum);
            viewHolder.textView1= (TextView) view.findViewById(R.id.orderDt);
            viewHolder.textView2= (TextView) view.findViewById(R.id.namePa);
            viewHolder.textView3= (TextView) view.findViewById(R.id.orderS);
            viewHolder.textView4= (TextView) view.findViewById(R.id.idNum);
            viewHolder.textView5= (TextView) view.findViewById(R.id.fligthNam);
            viewHolder.textView6= (TextView) view.findViewById(R.id.ticketPr);
            viewHolder.textView7= (TextView) view.findViewById(R.id.startPla);
            viewHolder.textView8= (TextView) view.findViewById(R.id.endPla);
            viewHolder.textView9= (TextView) view.findViewById(R.id.seatN);
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
        viewHolder.textView6.setText(orders.getFlight().getPrice().toString());
        viewHolder.textView7.setText(orders.getFlight().getStartCity());
        viewHolder.textView8.setText(orders.getFlight().getEndCity());
        viewHolder.textView9.setText(orders.getSeat()+"号");
        viewHolder.textView3.setText("已出行");
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
    }

}
