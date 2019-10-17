package com.mobile.mvpwithdatabinding.register.views.interfaces;

public interface RegisterContract {
    interface ViewModel {
        void registerAndGoToHome();

        void showToast(String message);

        void pickImage();
    }

    interface Presenter {
        void doLogin();

        void filePicker();

        void onStop();
        void onDestroy();
    }
}
