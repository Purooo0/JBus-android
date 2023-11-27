package com.AdheliaPutriMaylaniJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BusType;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Facility;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Station;

import java.util.ArrayList;
import java.util.List;

public class AddBusActivity extends AppCompatActivity {
    private Spinner busTypeSpinner;
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private CheckBox acCheckBox, wifiCheckBox, toiletCheckBox, lcdCheckBox, coolboxCheckBox, lunchCheckBox, largebaggageCheckBox, electricsocketCheckBox;
    private List<Station> stationList = new ArrayList<>();
    private int selectedDeptStationID;
    private int selectedArrStationID;
    private List<Facility> selectedFacilities = new ArrayList<>();
    private BusType[] busType = BusType.values();
    private BusType selectedBusType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        busTypeSpinner = findViewById(R.id.bus_type_dropdown);
        ArrayAdapter adBus = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, busType);
        adBus.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        busTypeSpinner.setAdapter(adBus);
        busTypeSpinner.setOnItemSelectedListener(busTypeOISL);

        departureSpinner = findViewById(R.id.departure_spinner);
        arrivalSpinner = findViewById(R.id.arrival_spinner);

        acCheckBox = findViewById(R.id.ac_checkbox);
        wifiCheckBox = findViewById(R.id.wifi_checkbox);
        toiletCheckBox = findViewById(R.id.toilet_checkbox);
        lcdCheckBox = findViewById(R.id.lcd_checkbox);
        coolboxCheckBox = findViewById(R.id.coolbox_checkbox);
        lunchCheckBox = findViewById(R.id.lunch_checkbox);
        largebaggageCheckBox = findViewById(R.id.largeBaggage_checkbox);
        electricsocketCheckBox = findViewById(R.id.electricSocket_checkbox);

        Button addButton = findViewById(R.id.add_bus_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBus();
            }
        });
    }

    private AdapterView.OnItemSelectedListener busTypeOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            selectedBusType = busType[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void addBus() {
        Intent intent = new Intent(AddBusActivity.this, ManageBusActivity.class);
        startActivity(intent);
        finish();
    }
}