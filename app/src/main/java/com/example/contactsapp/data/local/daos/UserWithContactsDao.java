package com.example.contactsapp.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.contactsapp.data.entities.UserWithContacts;

import java.util.List;

@Dao
public interface UserWithContactsDao {

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    LiveData<List<UserWithContacts>> getUserWithContacts(Integer userId);

}
