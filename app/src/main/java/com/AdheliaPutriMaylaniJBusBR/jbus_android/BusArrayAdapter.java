package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    public BusArrayAdapter(@NonNull Context context, List<Bus> buses) {
        super(context, 0, buses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View currentItemView = convertView;
        if(currentItemView == null){
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view,parent,false);
        }

        Bus currentBus = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.textView1);
        textView1.setText(currentBus.toString());

        TextView textView2 = currentItemView.findViewById(R.id.textView2);
        textView2.setText(currentBus.name);

        return currentItemView;
    }
}