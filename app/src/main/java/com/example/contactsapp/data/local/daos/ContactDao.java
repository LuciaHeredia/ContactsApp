package com.example.contactsapp.data.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.User;

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

    @Transaction
    @Query("SELECT * FROM contact_table WHERE contactId = :contactId")
    Contact getContactById(Integer contactId);

    /*@Transaction
    @Insert
    void updateContact(Contact contact);*/

    /*@Transaction
    @Query("DELETE FROM contact_table")
    void deleteAllContacts();*/

}
