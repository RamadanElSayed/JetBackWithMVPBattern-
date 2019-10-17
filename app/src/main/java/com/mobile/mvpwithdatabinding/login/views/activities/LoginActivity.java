package com.mobile.mvpwithdatabinding.login.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mvpwithdatabinding.constants.Constant;
import com.mobile.mvpwithdatabinding.home.views.activities.HomeActivity;
import com.mobile.mvpwithdatabinding.R;
import com.mobile.mvpwithdatabinding.login.views.interfaces.LoginContract;
import com.mobile.mvpwithdatabinding.login.presenters.LoginPresenter;
import com.mobile.mvpwithdatabinding.register.views.activities.RegisterActivity;
import com.mobile.mvpwithdatabinding.databinding.ActivityLoginBinding;
import com.mobile.mvpwithdatabinding.model.Contact;

public class LoginActivity extends AppCompatActivity implements LoginContract.ViewModel {

    private LoginPresenter mPresenter;
    private ActivityLoginBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mPresenter = new LoginPresenter(this);
        mBinding.setPresenter(mPresenter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void login() {
        startActivity(
                new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void showLoginFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
