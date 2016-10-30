package com.example.twmoore.localrestaurants;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lackeyw on 10/30/16.
 */

public class RestuarantAdapter extends ArrayAdapter{

    ArrayList list = new ArrayList<Object>();

    public RestuarantAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class dataHandler{

        TextView name;
        TextView distance;
        TextView rating;
        TextView doesDeliv;
    }

    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        dataHandler handler;
        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout, parent, false);
            handler = new dataHandler();
            handler.name = (TextView)row.findViewById(R.id.res_name);
            handler.distance = (TextView)row.findViewById(R.id.distance);
            handler.rating = (TextView)row.findViewById(R.id.close_time);
            handler.doesDeliv = (TextView)row.findViewById(R.id.does_delivery);
            row.setTag(handler);
        }

        else{
            handler = (dataHandler)row.getTag();
        }

        Restaurant restaurant;
        restaurant = (Restaurant)this.getItem(position);
        handler.name.setText(restaurant.getName());
        handler.doesDeliv.setText(restaurant.delivString());
        handler.rating.setText(restaurant.ratingString());
        handler.distance.setText(restaurant.distanceString());

        return row;
    }
}
