package com.example.contactsapp.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Transaction;

import com.example.contactsapp.data.entities.Contact;

@Dao
public interface ContactDao {
    @Transaction
    @Insert
    void insertContact(Contact contact);

    /*@Transaction
    @Insert
    void updateContact(Contact contact);*/

    /*@Transaction
    @Query("DELETE FROM contact_table")
    void deleteAllContacts();*/

}
