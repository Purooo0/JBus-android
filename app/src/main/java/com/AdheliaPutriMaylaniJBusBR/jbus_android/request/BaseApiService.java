package com.AdheliaPutriMaylaniJBusBR.jbus_android.request;
import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.Account;

import com.AdheliaPutriMaylaniJBusBR.jbus_android.model.BaseResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<BaseResponse<Account>> getAccountbyId (@Path("id") int id);

    @POST("account/login")
    Call<BaseResponse<Account>> login (
            @Query("email") String email,
            @Query("password") String password);

    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password);

    @POST("account/{id}/topUp")
    Call<BaseResponse<Boolean>> topUp(
            @Path("id") int id,
            @Query("balance") int balance);
}