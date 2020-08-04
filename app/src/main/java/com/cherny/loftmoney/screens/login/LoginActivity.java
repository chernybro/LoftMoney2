package com.cherny.loftmoney.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cherny.loftmoney.LoftApp;
import com.cherny.loftmoney.MainActivity;
import com.cherny.loftmoney.R;
import com.cherny.loftmoney.remote.AuthResponse;

import java.util.Random;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;




public class LoginActivity extends AppCompatActivity implements LoginView{
    Button loginButtonView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LoginPresenter loginPresenter = new LoginPresenterImpl();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonView = findViewById(R.id.loginButtonView);
        loginPresenter.attachViewState(this);

        configureButton();

    }

    private void configureButton() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.performLogin(((LoftApp) getApplication()).getAuthApi());
            }
        });
    }

    @Override
    public void toggleSending(boolean isActive) {
        loginButtonView.setVisibility(isActive ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showSuccess(String token) {
        Toast.makeText(getApplicationContext(),"login success", Toast.LENGTH_SHORT);
        ((LoftApp) getApplication()).getSharedPreferences().edit().putString(LoftApp.TOKEN_KEY,token).apply();

        Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(mainIntent);
    }
}