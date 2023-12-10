package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Activity class for handling payment-related functionality.
 * This activity provides the user interface for processing payments in the JBus application.
 * It extends the AppCompatActivity class and is associated with the activity_payment layout.
 *
 * @author Adhelia Putri Maylani
 */
public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
}