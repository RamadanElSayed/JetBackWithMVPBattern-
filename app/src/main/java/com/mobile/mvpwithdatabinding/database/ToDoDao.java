package com.mobile.mvpwithdatabinding.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mobile.mvpwithdatabinding.model.Contact;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContactData(Contact contact);

    @Query("SELECT * FROM Contact ")
    Flowable<List<Contact>> getAllContacts();

    @Query("DELETE FROM Contact WHERE mId= :id")
    void deleteContactId(int id);

    @Update
    void update(Contact contact);
 }



