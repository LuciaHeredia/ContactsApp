package com.example.contactsapp.data.local.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.contactsapp.data.entities.User;

import java.util.List;

@Dao
public interface UserDao {

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

    @Transaction
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    User getUserById(Integer userId);

    @Transaction
    @Query("SELECT * FROM user_table WHERE username = :username")
    User getUserByUsername(String username);

}
