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
import android.widget.Button;

import com.cherny.loftmoney.remote.AuthApi;
import com.cherny.loftmoney.remote.AuthResponse;
import com.cherny.loftmoney.screens.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    AuthApi authApi;

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
            }
        });

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(LoftApp.TOKEN_KEY, "");
        if (TextUtils.isEmpty(token)) {
            auth();
        } else {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void checkToken() {
        String token = ((LoftApp) getApplication()).getSharedPreferences().getString((LoftApp.TOKEN_KEY),"");

        if(TextUtils.isEmpty(token)){
            routeToLogin();
        }
        else {
            routeToMain();
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

    private void routeToMain() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    private void routeToLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }
}