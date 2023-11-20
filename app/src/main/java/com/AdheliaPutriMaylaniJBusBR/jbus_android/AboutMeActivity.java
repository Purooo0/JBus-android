package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView initialTextView = findViewById(R.id.initial);
        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);

        String initialUsername = "Adhelia";
        initialTextView.setText(initialUsername.substring(0, 1));

        String username = "Puro";
        String email = "bapakkuGarena@ui.ac.id";
        String balance = "IDR 100.000.000.000.000";

        usernameTextView.setText(username);
        emailTextView.setText(email);
        balanceTextView.setText(balance);
    }
}