package com.cherny.loftmoney.remote;

import com.cherny.loftmoney.cells.money.MoneyCellModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kotlin.jvm.JvmMultifileClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyApi {

    @GET("./items")
    Single<List<MoneyItem>> getMoney(@Query("type") String type, @Query("auth-token") String token);

    @GET("items")
    Call<List<MoneyCellModel>> getItems(@Query("type") String type, @Query("auth-token") String token);

    @POST("./items/add")
    @FormUrlEncoded
    Completable addMoney(@Field("auth-token") String token,
                         @Field("price") String price,
                         @Field("name") String name,
                         @Field("type") String type);



    @POST("items/add")
    Call<AuthResponse> addItem(@Body MoneyItem moneyItem, @Query("auth-token") String token);


}
