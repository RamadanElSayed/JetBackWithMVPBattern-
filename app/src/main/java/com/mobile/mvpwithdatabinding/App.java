package com.mobile.mvpwithdatabinding;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.mobile.mvpwithdatabinding.database.ToDoDao;
import com.mobile.mvpwithdatabinding.database.ToDoDataBase;

public class App extends Application {

    private static App mSelf;
    private static ToDoDao toDoDao;
    public static App self() {
        return mSelf;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSelf = this;
        setRoamingData();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private void setRoamingData() {
        ToDoDataBase toDoDataBase = Room.databaseBuilder(getApplicationContext(),
                ToDoDataBase.class, "contactapp_db").build();

        toDoDao = toDoDataBase.toDoDao();
    }

    public static ToDoDao getToDoDoa() {
        return toDoDao;
    }

}
