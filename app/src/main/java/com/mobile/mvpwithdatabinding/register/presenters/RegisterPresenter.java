package com.mobile.mvpwithdatabinding.register.presenters;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import com.mobile.mvpwithdatabinding.App;
import com.mobile.mvpwithdatabinding.ContactManagerRepository;
import com.mobile.mvpwithdatabinding.R;
import com.mobile.mvpwithdatabinding.model.Contact;
import com.mobile.mvpwithdatabinding.register.views.interfaces.RegisterContract;
import java.util.ArrayList;
import java.util.List;

public class RegisterPresenter implements RegisterContract.Presenter {
    public ObservableField<String> username;
    public ObservableField<String> mobile;
    public ObservableField<String> email;
    public ObservableField<String> password;
    public ObservableField<String> imageUrl;
    public ObservableBoolean isImageSelected;
    public ObservableBoolean isNewOrEdit;
    private RegisterContract.ViewModel mViewModel;
    private List<Contact> mContactList;
    private Boolean isUpdate = false;
    private Contact mContact;
    private ContactManagerRepository contactManagerRepository;
    public RegisterPresenter(RegisterContract.ViewModel viewModel, Contact contact) {
        mViewModel = viewModel;
        mContactList=new ArrayList<>();
        contactManagerRepository =new ContactManagerRepository();
        initFields();
        setValues(contact);
    }

    private void initFields() {
        username = new ObservableField<>();
        mobile = new ObservableField<>();
        email = new ObservableField<>();
        password = new ObservableField<>();
        imageUrl = new ObservableField<>();
        isImageSelected = new ObservableBoolean();
        isNewOrEdit = new ObservableBoolean();
        contactManagerRepository.getListContacts(value -> mContactList=value);
    }

    private void setValues(Contact contact) {
        if (contact == null) return;
        mContact = contact;
        username.set(contact.getName());
        mobile.set(contact.getMobile());
        email.set(contact.getEmail());
        if (TextUtils.isEmpty(contact.getImageUrl())) {
            isImageSelected.set(false);
        } else {
            imageUrl.set(contact.getImageUrl());
            isImageSelected.set(true);
        }
        isNewOrEdit.set(true);
        isUpdate = true;
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(username.get())) {
            mViewModel.showToast(App.self().getString(R.string.err_username));
            return false;
        }
        if (TextUtils.isEmpty(mobile.get())) {
            mViewModel.showToast(App.self().getString(R.string.err_phone));
            return false;
        }
        if (TextUtils.isEmpty(email.get())) {
            mViewModel.showToast(App.self().getString(R.string.err_email));
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            mViewModel.showToast(App.self().getString(R.string.err_password));
            return false;
        }

        if (!isUpdate) {
            for (Contact contact : mContactList) {
                if (contact.getEmail().equals(email.get())) {
                    mViewModel.showToast(App.self().getString(R.string.err_user_exist));
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void doLogin() {
        if (isValidate()) {
           Contact contact=new Contact();
           contact.setEmail(email.get());
           contact.setImageUrl(imageUrl.get());
           contact.setMobile(mobile.get());
           contact.setName(username.get());
           contact.setPassword(password.get());

            Log.v("contact",contact.toString());
            if (isUpdate) {
                contact.setId(mContact.getId());
                contactManagerRepository.saveContact(contact, true, value -> mViewModel.registerAndGoToHome());
                return;
            }
            Log.v("contact",contact.getEmail().toString());
            contactManagerRepository.saveContact(contact, false, value -> {mViewModel.registerAndGoToHome();
            mViewModel.showToast("Done");
            });

        }
    }


    @Override
    public void filePicker() {
        mViewModel.pickImage();
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