package com.cherny.loftmoney.screens.login;

import com.cherny.loftmoney.remote.AuthApi;

public interface LoginView {
    void toggleSending(boolean isActive);
    void showMessage(String message);
    void showSuccess(String token);

}
