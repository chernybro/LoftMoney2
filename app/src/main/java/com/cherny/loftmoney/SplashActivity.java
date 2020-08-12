package com.cherny.loftmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.cherny.loftmoney.remote.AuthApi;
import com.cherny.loftmoney.remote.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authApi = ((LoftApp)getApplication()).getAuthApi();

        AppCompatButton authButton = findViewById(R.id.loginButtonView);
        authButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                finish();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(LoftApp.TOKEN_KEY, "");
        if (TextUtils.isEmpty(token)) {
            auth();
        } else {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    private void auth() {
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Call<AuthResponse> auth = authApi.auth(androidId);
        auth.enqueue(new Callback<AuthResponse>() {

            @Override
            public void onResponse(
                    final Call<AuthResponse> call, final Response<AuthResponse> response
            ) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        SplashActivity.this).edit();
                editor.putString((LoftApp.TOKEN_KEY), response.body().getAccessToken());
                editor.apply();
            }

            @Override
            public void onFailure(final Call<AuthResponse> call, final Throwable t) {

            }
        });
    }

}