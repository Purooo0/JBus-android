package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    TextView name, email, balance;
    Button AmountButton;
    EditText AmountText;
    public static Account loggedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);

        usernameTextView.setText(LoginActivity.loggedAccount.name);
        emailTextView.setText(LoginActivity.loggedAccount.email);
        balanceTextView.setText("0");

    }

    protected Boolean requestTopUp(int id, int balance){
        System.out.println("Id: "+ id);
        System.out.println("TopUp: "+ balance);
        mApiService.topUp(id, balance).enqueue(new Callback<BaseResponse<Boolean>>() {
            @Override
            public void onResponse(Call<BaseResponse<Boolean>> call, Response<BaseResponse<Boolean>> response) {
                if(response.isSuccessful()){
                    BaseResponse<Boolean> Result = response.body();
                    System.out.println("Top Up Successful");
                    loggedAccount.balance += balance;
                    Toast.makeText(mContext, "Your top up is successful", Toast.LENGTH_SHORT).show();
                    Intent startIntent = getIntent();
                    finish();
                    startActivity(startIntent);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Boolean>> call, Throwable t) {
                System.out.println("Top Up Error");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Your top up is failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}