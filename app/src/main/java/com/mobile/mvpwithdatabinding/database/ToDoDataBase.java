package com.mobile.mvpwithdatabinding.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mobile.mvpwithdatabinding.model.Contact;

@Database(entities = {Contact.class}, version = 1,exportSchema = false)
public abstract class ToDoDataBase extends RoomDatabase {
    public abstract ToDoDao toDoDao();
}
