package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.BaseApiService;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.request.UtilsApi;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginActivity represents the screen for user login.
 * It handles user authentication and navigation to other activities.
 *
 * @author Adhelia Putri Maylani
 */
public class LoginActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText email, password;
    private TextView registerNow = null;
    private Button loginButton = null;
    static Account loggedAcccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Hide the action bar
        getSupportActionBar().hide();

        // Load the components to the variables
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerNow = findViewById(R.id.register_now);
        loginButton = findViewById(R.id.login_button);

        // Other variable
        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Adding listener
        registerNow.setOnClickListener(v -> {
            moveActivity(this, RegisterActivity.class);
        });

        loginButton.setOnClickListener(v -> {
            handleLogin();
        });
    }

    /**
     * Moves to another activity.
     *
     * @param ctx The context in which the activity is called.
     * @param cls The class of the target activity.
     */
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    /**
     * Displays a Toast message.
     *
     * @param ctx     The context in which the Toast is displayed.
     * @param message The message to be displayed.
     */
    private void viewToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the login process by making a request to the server.
     */
    protected void handleLogin() {
        // handling empty field
        String emailS = email.getText().toString();
        String passwordS = password.getText().toString();

        if (emailS.isEmpty() || passwordS.isEmpty()) {
            viewToast(mContext, "Field cannot be empty");
            return;
        }

        // Make a login request to the server
        mApiService.login(emailS, passwordS).enqueue(new Callback<BaseResponse<Account>>() {
            @Override
            public void onResponse(Call<BaseResponse<Account>> call, Response<BaseResponse<Account>> response) {
                // Handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    viewToast(mContext, "Application error " + response.code());
                    return;
                }

                // Handle successful response (code 200)
                BaseResponse<Account> res = response.body();
                if (res.success) {
                    loggedAcccount = res.payload;
                    viewToast(mContext, "Welcome to JBus "+loggedAcccount.name);
                    email.setText("");
                    password.setText("");
                    startActivity(new Intent(mContext, MainActivity.class));
                } else {
                    viewToast(mContext, res.message);
                }
            }

            // Handle client failure to make a request
            @Override
            public void onFailure(Call<BaseResponse<Account>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}