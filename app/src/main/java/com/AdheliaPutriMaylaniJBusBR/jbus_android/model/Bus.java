package com.AdheliaPutriMaylaniJBusBR.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id;
    public int accountId;
    public String name;
    private List<Facility> facilities;
    private Price price;
    private int capacity;
    private BusType busType;
    private Station departure;
    private Station arrival;
    public List<Schedule> schedules;

    // Tambahkan metode getter untuk accountId
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Implementasi metode equals dan hashCode jika diperlukan

    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.setName("Bus " + i);
            busList.add(bus);
        }

        return busList;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}