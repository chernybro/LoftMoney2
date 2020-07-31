package com.cherny.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cherny.loftmoney.remote.AuthResponse;

import java.util.Random;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;




public class LoginActivity extends AppCompatActivity {
    Button loginButtonView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButtonView = findViewById(R.id.loginButtonView);

        configureButton();

    }

    private void configureButton() {
        loginButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String socialUserId = String.valueOf(new Random().nextInt());
                compositeDisposable.add(((LoftApp) getApplication()).getAuthApi().performLogin(socialUserId)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<AuthResponse>() {
                            @Override
                            public void accept(AuthResponse authResponse) throws Exception {
                                ((LoftApp)  getApplication()).getSharedPreferences()
                                .edit()
                                .putString(LoftApp.TOKEN_KEY, authResponse.getAccessToken())
                                .apply();

                                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(mainIntent);

                                Log.e("TAG","result" + authResponse);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getApplicationContext(),throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                Log.e("Error", "error" + throwable.getLocalizedMessage());
                            }
                        }));
            }
        });
    }
}