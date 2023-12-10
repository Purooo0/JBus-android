package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AboutMeActivity class displays user account details and provides functionality
 * for topping up the account balance and managing bus-related activities.
 *
 * @author Adhelia Putri Maylani
 */
public class AboutMeActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    TextView initial = null;
    TextView username = null;
    TextView email = null;
    TextView balance = null;
    TextView statusRenter = null;
    TextView registerCompany = null;
    EditText amount = null;
    Button topUpButton = null;
    Button manageBus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // Initialize UI components
        initial = this.findViewById(R.id.initial_name);
        username = this.findViewById(R.id.username);
        email = this.findViewById(R.id.email);
        balance = this.findViewById(R.id.balance);
        amount = this.findViewById(R.id.top_up_amount);
        statusRenter = this.findViewById(R.id.status_renter);
        registerCompany = this.findViewById(R.id.register_company);
        topUpButton = this.findViewById(R.id.top_up_button);
        manageBus = this.findViewById(R.id.button_manage_bus);

        mContext = this;
        mApiService = UtilsApi.getApiService();
        handleRefreshAccount();

        // Set onClickListener for top-up button
        topUpButton.setOnClickListener(v->{
            handleTopUp();
        });

        // Set onClickListener for register company button
        registerCompany.setOnClickListener(v->{
            startActivity(new Intent(mContext, RegisterRenterActivity.class));
        });

        // Set onClickListener for manage bus button
        manageBus.setOnClickListener(v->{
            startActivity(new Intent(mContext, ManageBusActivity.class));
        });
    }

    /**
     * Loads data into UI components based on the provided account information.
     *
     * @param a The Account object containing user account details.
     */
    private void loadData(Account a) {
        initial.setText(""+a.name.toUpperCase().charAt(0));
        username.setText(a.name);
        email.setText(a.email);
        balance.setText("IDR "+a.balance);
        if (a.company == null) {
            statusRenter.setText("You're not registered as a renter");
            manageBus.setVisibility(View.GONE);
            registerCompany.setVisibility(View.VISIBLE);
        } else {
            statusRenter.setText("You're already registered as a renter");
            manageBus.setVisibility(View.VISIBLE);
            registerCompany.setVisibility(View.GONE);
        }
        manageBus.setVisibility(a.company != null ? View.VISIBLE : View.GONE);
    }

    /**
     * Handles the top-up functionality by sending a request to the server
     * and updating the UI based on the server's response.
     */
    protected void handleTopUp() {
        String amountS = amount.getText().toString();
        Double amountD = amountS.isEmpty() ? 0d : Double.parseDouble(amountS);

        mApiService.topUp(LoginActivity.loggedAcccount.id, amountD).enqueue(new Callback<BaseResponse<Double>>() {
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<Double> res = response.body();
                    if (res.success) {
                        balance.setText("IDR " + res.payload);
                    }
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response (e.g., HTTP error)
                    Toast.makeText(mContext, "Failed to top up. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                // Handle network errors or other failures
                Toast.makeText(mContext, "Network error or failed to top up. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Handles the retrieval of user account information from the server
     * and updates the UI based on the server's response.
     */
    protected void handleRefreshAccount() {
        if(LoginActivity.loggedAcccount != null){
            BaseApiService mApiService = UtilsApi.getApiService();
            mApiService.getAccountById(LoginActivity.loggedAcccount.id).enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(mContext, "App error", Toast.LENGTH_SHORT).show();
                        Log.d("GetAccountById", "onResponse: " + response.body());
                        return;
                    } else {
                        Log.e("GetAccountById", "onResponse: Unsuccessful response");
                    }

                    // if success, get the response body
                    Account responseAccount = response.body();
                    loadData(responseAccount);
                }

                // method for handling error talking to the server
                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    Log.e("AboutMeActivity", "Get Account API Call Failed", t);
                    Toast.makeText(mContext, "Failed to fetch account details. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.e("GetAccountById", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(mContext, "User account is not available. Please log in again.", Toast.LENGTH_SHORT).show();
        }
    }
}