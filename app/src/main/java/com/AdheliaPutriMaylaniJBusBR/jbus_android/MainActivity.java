package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.R.id;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search_menu:
                Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.person_menu:
                Toast.makeText(this, "User Profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.payment_menu:
                Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}