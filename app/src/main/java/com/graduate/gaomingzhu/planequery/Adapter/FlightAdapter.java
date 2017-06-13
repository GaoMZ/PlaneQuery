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

import com.graduate.gaomingzhu.planequery.R;
import com.graduate.gaomingzhu.planequery.model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GaoMingzhu on 2017/4/19.
 * email:2713940331@qq.com
 */


public class FlightAdapter extends ArrayAdapter<Flight>{

    private  int resourceId;


    public FlightAdapter(@NonNull Context context, @LayoutRes int resource,List<Flight> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        //方法一
        /*Flight flight = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView textView= (TextView) view.findViewById(R.id.fligthName);
        TextView textView1= (TextView) view.findViewById(R.id.startPlace);
        TextView textView2= (TextView) view.findViewById(R.id.endPlace);
        TextView textView3= (TextView) view.findViewById(R.id.startTime);
        TextView textView4= (TextView) view.findViewById(R.id.endTime);
        TextView textView5= (TextView) view.findViewById(R.id.ticketPrice);
        TextView textView6= (TextView) view.findViewById(R.id.ticketRemaining);
        textView.setText(flight.getFlightName());
        textView1.setText(flight.getStartCity());
        textView2.setText(flight.getEndCity());
        textView3.setText(flight.getStartDate().getDate().toString());
        textView4.setText(flight.getEndDate().getDate().toString());
        textView5.setText(flight.getPrice().toString()+"元");
        textView6.setText(flight.getRemaining().toString()+"张");*/
        Flight flight=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) view.findViewById(R.id.fligthName);
            viewHolder.textView1= (TextView) view.findViewById(R.id.startPlace);
            viewHolder.textView2= (TextView) view.findViewById(R.id.endPlace);
            viewHolder.textView3= (TextView) view.findViewById(R.id.startTime);
            viewHolder.textView4= (TextView) view.findViewById(R.id.endTime);
            viewHolder.textView5= (TextView) view.findViewById(R.id.ticketPrice);
            viewHolder.textView6= (TextView) view.findViewById(R.id.ticketRemaining);

           view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.textView.setText(flight.getFlight());
        viewHolder.textView1.setText(flight.getStartCity());
        viewHolder.textView2.setText(flight.getEndCity());
        viewHolder.textView3.setText(flight.getStartDate().getDate().toString());
        viewHolder.textView4.setText(flight.getEndDate().getDate().toString());
        viewHolder.textView5.setText(flight.getPrice().toString()+"元");
        viewHolder.textView6.setText(flight.getRemaining().toString()+"张");
        return view;
    }
    class ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;

    }
}
