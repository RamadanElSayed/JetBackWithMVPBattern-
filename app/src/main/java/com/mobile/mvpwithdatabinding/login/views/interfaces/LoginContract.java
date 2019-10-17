package com.mobile.mvpwithdatabinding.login.views.interfaces;

public interface LoginContract {
    interface ViewModel {
        void login();
        void showLoginFailed(String error);
        void register();
    }
    interface Presenter {
        void doLogin();
        void register();
        void onStop();
        void onDestroy();
    }
}
