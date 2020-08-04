package com.cherny.loftmoney.screens.login;

import android.util.Log;

import com.cherny.loftmoney.remote.AuthApi;
import com.cherny.loftmoney.remote.AuthResponse;

import java.util.Random;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter  {

    private LoginView loginView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public void attachViewState(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void performLogin(AuthApi authApi) {
        if(loginView == null){
            return;
        }

        loginView.toggleSending(true);
        String socialUserId = String.valueOf(new Random().nextInt());
        compositeDisposable.add(authApi.performLogin(socialUserId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AuthResponse>() {
                    @Override
                    public void accept(AuthResponse authResponse) throws Exception {
                        loginView.toggleSending(false);
                        loginView.showSuccess(authResponse.getAccessToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.toggleSending(false);
                        loginView.showMessage(throwable.getLocalizedMessage());
                    }
                }));
    }
}
