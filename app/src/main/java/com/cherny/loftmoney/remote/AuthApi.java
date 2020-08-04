package com.cherny.loftmoney.remote;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthApi {

    @GET("./auth")
    Single<AuthResponse> performLogin(@Query("social_user_id") String socialUserId);

    @GET("auth")
    Call<AuthResponse> auth(@Query("social_user_id") String userId);

}
