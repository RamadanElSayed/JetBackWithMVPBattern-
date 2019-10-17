package com.mobile.mvpwithdatabinding.home.views.interfaces;

import com.mobile.mvpwithdatabinding.model.Contact;

import java.util.List;

public interface HomeContract {
    interface ViewModel {
        void logout();
        void editUser(Contact contact);
        void cardClicked(Contact contact);
        void onListContactReceived(List<Contact> contactList);
    }
    interface Presenter {

        void getListOfContact();
        void onStop();
        void onDestroy();
    }
}
