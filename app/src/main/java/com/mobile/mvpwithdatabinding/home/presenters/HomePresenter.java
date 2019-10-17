package com.mobile.mvpwithdatabinding.home.presenters;

import android.util.Log;

import androidx.databinding.ObservableField;

import com.mobile.mvpwithdatabinding.ContactManagerRepository;
import com.mobile.mvpwithdatabinding.home.views.interfaces.HomeContract;
import com.mobile.mvpwithdatabinding.model.Contact;

public class HomePresenter implements HomeContract.Presenter {
    public ObservableField<String> imageUrl;
    private Contact mContact;
    private HomeContract.ViewModel mViewModel;
    private ContactManagerRepository contactManagerRepository;
    public HomePresenter(HomeContract.ViewModel viewModel) {
        mViewModel = viewModel;
        imageUrl = new ObservableField<>();
        contactManagerRepository = new ContactManagerRepository();
    }

    @Override
    public void getListOfContact() {

        contactManagerRepository.getListContacts(value -> {
            mContact=value.get(0);
            Log.v("mContact",mContact.getImageUrl()+",sksxjaksdahj");
            imageUrl.set(mContact.getImageUrl());
            mViewModel.onListContactReceived(value);

        });
    }

    @Override
    public void onStop() {
        contactManagerRepository.onStop();
    }

    @Override
    public void onDestroy() {
        contactManagerRepository.onDestroy();
    }
}