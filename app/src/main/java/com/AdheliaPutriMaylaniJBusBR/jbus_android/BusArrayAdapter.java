package com.AdheliaPutriMaylaniJBusBR.jbus_android;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * BusArrayAdapter is a custom ArrayAdapter for displaying Bus objects in a ListView or other AdapterView.
 * It uses a custom layout (manage_list_view.xml) to define the appearance of each item in the list.
 *
 * @author Adhelia Putri Maylani
 */
public class BusArrayAdapter extends ArrayAdapter<Bus> {
    // ViewHolder class to hold references to views for efficient recycling
    private static class ViewHolder {
        ImageView numbersImage;
        TextView textView1;
        TextView textView2;
    }

    /**
     * Constructor for the BusArrayAdapter.
     *
     * @param context The context in which the adapter is created.
     * @param buses   The list of Bus objects to be displayed.
     */
    public BusArrayAdapter(@NonNull Context context, List<Bus> buses) {
        super(context, 0, buses);
    }

    /**
     * Overrides the getView method to provide a custom view for each item in the adapter.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup.
     * @return The populated view for display.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        // Check if the view is being recycled; if not, inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_list_view, parent, false);

            // Create a ViewHolder and store references to the views in it
            holder = new ViewHolder();
            holder.numbersImage = convertView.findViewById(R.id.bus);
            holder.textView1 = convertView.findViewById(R.id.Textview1);
            holder.textView2 = convertView.findViewById(R.id.Textview2);

            // Set the ViewHolder as a tag for the convertView
            convertView.setTag(holder);
        } else {
            // If the view is being recycled, retrieve the ViewHolder from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the current Bus object from the adapter's data set
        Bus currentBus = getItem(position);

        // Populate the views with data from the current Bus object
        if (currentBus != null) {
            holder.numbersImage.setImageResource(R.drawable.baseline_directions_bus_24);
            holder.textView1.setText(String.valueOf(currentBus.getAccountId()));
            holder.textView2.setText(currentBus.getName());
        }

        // Return the populated view for display
        return convertView;
    }
}