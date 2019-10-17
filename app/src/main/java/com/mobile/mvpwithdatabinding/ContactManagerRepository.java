package com.mobile.mvpwithdatabinding;
import android.util.Log;
import android.webkit.ValueCallback;
import com.mobile.mvpwithdatabinding.database.ToDoDao;
import com.mobile.mvpwithdatabinding.model.Contact;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactManagerRepository {
    private CompositeDisposable compositeDisposable;
    private ToDoDao todoDatabaseQueries;
    public ContactManagerRepository() {
        todoDatabaseQueries=App.getToDoDoa();
        //compositeDisposable= new CompositeDisposable();
    }

    public  void saveContact(Contact contact, boolean isFromUpdate,ValueCallback<Boolean> callback) {

        compositeDisposable= new CompositeDisposable();
        Log.v("aBoolean",""+contact.getImageUrl());

        Flowable<Boolean> observableContact = Flowable.fromCallable(() -> {
            if (isFromUpdate)
                todoDatabaseQueries.update(contact);
            else
                todoDatabaseQueries.insertContactData(contact);

            Log.v("aBoolean", "" + contact.getEmail());
            return true;
        });

        Disposable observerSaveSession = observableContact.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(aBoolean -> {
                    if (aBoolean) {

                        Log.v("aBoolean",""+aBoolean);
                        callback.onReceiveValue(true);
                    }

                });

        compositeDisposable.add(observerSaveSession);
    }

    public void getListContacts(ValueCallback<List<Contact>> listValueCallback) {
        compositeDisposable= new CompositeDisposable();
        Flowable<List<Contact>> observableComingToDo = todoDatabaseQueries.getAllContacts();
        Disposable observerComingToDo = observableComingToDo.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(listContacts -> {
                    if (listContacts != null) {
                        listValueCallback.onReceiveValue(listContacts);
                    }
                });
        compositeDisposable.add(observerComingToDo);

    }

    public  void deleteContact(int id, ValueCallback<Boolean> callback) {
        compositeDisposable= new CompositeDisposable();
        Flowable<Boolean> observableDeleteSession = Flowable.fromCallable(() -> {
            todoDatabaseQueries.deleteContactId(id);
            return true;
        });

        Disposable observerDeleteSession = observableDeleteSession.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(aBoolean -> {
                    if (aBoolean != null) {
                        callback.onReceiveValue(aBoolean);
                    }

                });

        compositeDisposable.add(observerDeleteSession);
    }

    public void onStop() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }

    }


    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

}