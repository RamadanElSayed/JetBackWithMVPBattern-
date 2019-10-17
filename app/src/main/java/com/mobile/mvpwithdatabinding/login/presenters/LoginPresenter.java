package com.mobile.mvpwithdatabinding.login.presenters;
import android.text.TextUtils;
import androidx.databinding.ObservableField;
import com.mobile.mvpwithdatabinding.App;
import com.mobile.mvpwithdatabinding.ContactManagerRepository;
import com.mobile.mvpwithdatabinding.R;
import com.mobile.mvpwithdatabinding.login.views.interfaces.LoginContract;
import com.mobile.mvpwithdatabinding.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class LoginPresenter implements LoginContract.Presenter {

    public ObservableField<String> email;
    public ObservableField<String> password;
    private LoginContract.ViewModel mViewModel;
    private List<Contact> allContacts;
    private ContactManagerRepository contactManagerRepository;
    public LoginPresenter(LoginContract.ViewModel viewModel) {
        mViewModel = viewModel;
         contactManagerRepository = new ContactManagerRepository();
        initFields();
        contactManagerRepository.getListContacts(value -> allContacts=value);
    }

    private void initFields() {
        email = new ObservableField<>();
        password = new ObservableField<>();
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(email.get())) {
            mViewModel.showLoginFailed(App.self().getString(R.string.err_email));
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            mViewModel.showLoginFailed(App.self().getString(R.string.err_password));
            return false;
        }
        return true;
    }

    @Override
    public void doLogin() {
        if (isValidate()) {
            if (allContacts == null) allContacts = new ArrayList<>();
            Contact validContact = new Contact(email.get(), password.get());
            for (Contact contact : allContacts) {
                if (contact.getEmail().equals(validContact.getEmail()) && contact.getPassword()
                        .equals(validContact.getPassword())) {
                    mViewModel.login();
                    return;
                }
            }
            mViewModel.showLoginFailed(App.self().getString(R.string.err_login));
        }
    }

    @Override
    public void register() {
        mViewModel.register();
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
