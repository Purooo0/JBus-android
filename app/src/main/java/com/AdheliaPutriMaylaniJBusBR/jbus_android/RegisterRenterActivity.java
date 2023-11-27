package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Renter;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText companyName, address, phoneNumber;
    private Button renterButton = null;
    public static Renter registeredRenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        companyName = findViewById(R.id.CompanyName);
        address = findViewById(R.id.Address);
        phoneNumber = findViewById(R.id.PhoneNumber);
        renterButton = findViewById(R.id.registerRenterButton);
        renterButton.setOnClickListener(v -> handleRenter());
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleRenter(){
        String companyNameText = companyName.getText().toString();
        String addressText = address.getText().toString();
        String phoneNumberText = phoneNumber.getText().toString();

        if(companyNameText.isEmpty() || addressText.isEmpty() || phoneNumberText.isEmpty()){
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.renter(companyNameText, addressText, phoneNumberText).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                if(response.isSuccessful()) {
                    BaseResponse<Renter> res = response.body();
                    if (res.success) {
                        registeredRenter = res.payload;
                        Intent intent = new Intent(RegisterRenterActivity.this, AboutMeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(mContext, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
        }
}