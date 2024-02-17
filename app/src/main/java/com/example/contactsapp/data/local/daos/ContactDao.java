package com.example.contactsapp.data.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.contactsapp.data.entities.Contact;

@Dao
public interface ContactDao {
    @Transaction
    @Insert
    void insertContact(Contact contact);

    @Transaction
    @Update
    void updateContact(Contact contact);

    @Transaction
    @Delete
    void deleteContact(Contact contact);

}
