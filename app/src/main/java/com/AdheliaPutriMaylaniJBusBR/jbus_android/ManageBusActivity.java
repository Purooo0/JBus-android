package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Renter;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBusActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private ListView listView;
    private List<Bus> busList;
    private int accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus);

        mApiService = UtilsApi.getApiService();
        listView = findViewById(R.id.listView);

        int accountId = getAccountIdFromPreferences();
        getMyBusList(accountId);

        Button addBusButton = findViewById(R.id.addBusButton);
        addBusButton.setOnClickListener(v -> {
            moveActivity(this, AddBusActivity.class);
        });
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    private void getMyBusList(int accountId){
        Call<List<Bus>> call = mApiService.getMyBus(accountId);
        call.enqueue(new Callback<List<Bus>>() {
            @Override
            public void onResponse(Call<List<Bus>> call, Response<List<Bus>> response) {
                if(response.isSuccessful()){
                    List<Bus> myBusList = response.body();
                } else {
                    Toast.makeText(ManageBusActivity.this, "Failed to get bus list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bus>> call, Throwable t) {
                Toast.makeText(ManageBusActivity.this, "Erorr: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getAccountIdFromPreferences() {
        return 1;
    }
}