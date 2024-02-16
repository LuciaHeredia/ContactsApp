package com.example.contactsapp.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.data.entities.UserWithContacts;

import java.util.List;

@Dao
public interface UserDao {

    /* User */

    @Transaction
    @Insert
    void insertUser(User user);

    @Transaction
    @Update
    void updateUser(User user);

    @Transaction
    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();


    /* UserWithContacts */

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    LiveData<List<UserWithContacts>> getUserWithContacts(Integer userId);

    /*@Transaction
    @Query("DELETE FROM contact_table")
    void deleteAllContacts();*/

    /* Contact */

    @Transaction
    @Insert
    void insertContact(Contact contact);

    /*@Transaction
    @Insert
    void updateContact(Contact contact);*/

}
