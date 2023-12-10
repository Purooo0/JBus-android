package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * Activity class for registering a Renter account in the JBus application.
 * This activity allows Renters to register by providing company information such as
 * company name, address, and phone number.
 * Upon successful registration, Renters are redirected to the "AboutMeActivity".
 *
 * @author Adhelia Putri Maylani
 */
public class RegisterRenterActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText companyName, address, phoneNumber;
    private Button registerCompany = null;
    public static Account registeredRenterAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        companyName = this.findViewById(R.id.company_name);
        address = this.findViewById(R.id.address);
        phoneNumber = this.findViewById(R.id.phonenumber);
        registerCompany = this.findViewById(R.id.button_register);

        registerCompany.setOnClickListener(v->{
            handleRegisterCompany();
        });
    }

    protected void handleRegisterCompany() {
        // handling empty field
        String companyNameS = companyName.getText().toString();
        String addressS = address.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();

        if (companyNameS.isEmpty() || addressS.isEmpty() || phoneNumberS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(LoginActivity.loggedAcccount == null){
            Toast.makeText(mContext, "Account information not available", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.registerRenter(LoginActivity.loggedAcccount.id, companyNameS, addressS, phoneNumberS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Account> res = response.body();
                registeredRenterAccount = res.payload;
                if (res.success) mContext.startActivity(new Intent(mContext, AboutMeActivity.class));
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Log.e("API CALL", "Failed: " + t.getMessage());
                Toast.makeText(mContext, "Problem with the server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveActivity(Context ctx, Class<?> cls, String message){
        viewToast(this, message);
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }


}