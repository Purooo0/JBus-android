package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ManageBusActivity displays a list of buses that are managed by the logged-in user.
 * Users can view and manage their buses, and add new buses.
 * It includes options in the action bar for adding a new bus.
 *
 * @author Adhelia Putri Maylani
 */
public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView myBusListView;
    ImageView addSched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        getSupportActionBar().setTitle("Manage Bus");

        mContext = this;
        mApiService = UtilsApi.getApiService();
        myBusListView = this.findViewById(R.id.my_bus_list_view);

        // Load the user's buses
        loadMyBus();
    }

    /**
     * Load the buses managed by the logged-in user.
     */
    protected void loadMyBus() {
        mApiService.getMyBus(LoginActivity.loggedAcccount.id).enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if (!response.isSuccessful()) return;

                List<Bus> myBusList = response.body();
                MyArrayAdapter adapter = new MyArrayAdapter(mContext, myBusList);
                myBusListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {

            }
        });
    }

    /**
     * MyArrayAdapter is a custom ArrayAdapter to display a list of buses in ManageBusActivity.
     * It also handles item click events, allowing users to manage bus schedules.
     */
    private class MyArrayAdapter extends ArrayAdapter<Bus> {

        public MyArrayAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;

            // If the recyclable view is null, inflate the custom layout
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_manage_bus, parent, false);
            }

            // Get the position of the view from the ArrayAdapter
            Bus currentBus = getItem(position);

            // Assign the desired TextView 1 for the bus name
            TextView busName = currentItemView.findViewById(R.id.bus_name);
            busName.setText(currentBus.name);

            // Assign the desired ImageView for managing bus schedules
            ImageView addSched = currentItemView.findViewById(R.id.calendar);
            addSched.setOnClickListener(v->{
                Intent i = new Intent(mContext, ManageBusSchedule.class);
                i.putExtra("busId", currentBus.accountId);
                mContext.startActivity(i);
            });

            // Return the recyclable view
            return currentItemView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_bus_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_bus) {
            // Navigate to AddBusActivity when "Add Bus" is selected
            Intent intent = new Intent(mContext, AddBusActivity.class);
            intent.putExtra("type", "addBus");
            startActivity(intent);
            return true;
        } else return super.onOptionsItemSelected(item);
    }
}