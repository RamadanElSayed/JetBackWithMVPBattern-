package com.mobile.mvpwithdatabinding.home.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mobile.mvpwithdatabinding.constants.Constant;
import com.mobile.mvpwithdatabinding.R;
import com.mobile.mvpwithdatabinding.databinding.ActivityHomeBinding;
import com.mobile.mvpwithdatabinding.home.views.adapters.HomeAdapter;
import com.mobile.mvpwithdatabinding.home.views.interfaces.HomeContract;
import com.mobile.mvpwithdatabinding.home.presenters.HomePresenter;
import com.mobile.mvpwithdatabinding.login.views.activities.LoginActivity;
import com.mobile.mvpwithdatabinding.model.Contact;
import com.mobile.mvpwithdatabinding.register.views.activities.RegisterActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity  implements HomeContract.ViewModel {

    private HomePresenter mPresenter;
    private ActivityHomeBinding mBinding;
    private HomeAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mPresenter = new HomePresenter(this);
        mBinding.setPresenter(mPresenter);
        mPresenter.getListOfContact();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void editUser(Contact contact) {
        startActivity(new Intent(this, RegisterActivity.class).putExtra(Constant.EXTRA_CONTACT, contact));
        finish();
    }

    @Override
    public void cardClicked(Contact contact) {

        Toast.makeText(this, "You clicked " + contact.getImageUrl(),
                Toast.LENGTH_LONG).show();
        myRecyclerViewAdapter.addContact(contact);
    }

    @Override
    public void onListContactReceived(List<Contact> contactList) {

        myRecyclerViewAdapter = new HomeAdapter(this,contactList);

        mBinding.setAdapter(myRecyclerViewAdapter);
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

}