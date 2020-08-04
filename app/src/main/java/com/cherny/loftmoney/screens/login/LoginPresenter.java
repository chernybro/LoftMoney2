package com.cherny.loftmoney.screens.login;

import com.cherny.loftmoney.remote.AuthApi;

public interface LoginPresenter {
    void performLogin(AuthApi authApi);
    void attachViewState(LoginView loginView);
}
