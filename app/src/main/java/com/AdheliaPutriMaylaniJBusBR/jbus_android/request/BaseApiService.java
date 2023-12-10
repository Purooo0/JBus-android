package com.AdheliaPutriMaylaniJBusBR.jbus_android.request;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Bus;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BusType;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Facility;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @POST("account/Login")
    Call<BaseResponse<Account>> login (
            @Query("email") String email,
            @Query("password") String password);

    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password);

    @GET("account/{id}")
    Call<Account> getAccountById(@Path("id") int id);

    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Account>> registerRenter(
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber);

    @GET("bus/total")
    Call<Integer> numberOfBuses();

    @GET("bus/page")
    Call<List<Bus>> getBus(@Query("page") int page, @Query("size") int pageSize);

    @POST("bus/create")
    Call<BaseResponse<Bus>> addBus(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("capacity") int capacity,
            @Query("facilities") List<Facility> facilities,
            @Query("busType") BusType busType,
            @Query("price") int price,
            @Query("stationDepartureId") int stationDepartureId,
            @Query("stationArrivalId") int stationArrivalId
    );

    @GET("bus/getMyBus")
    Call<List<Bus>> getMyBus(@Query("accountId") int accountId);

    @GET("bus/{id}")
    Call<Bus> getBusbyId(@Path("id") int busId);

    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule(@Query("busId") int busId,
                                        @Query("time") String time);

    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp (@Path("id") int id, @Query("balance") double balance);

    @GET("station/getAll")
    Call<List<Station>> getAllStation();
}