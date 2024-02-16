package com.example.contactsapp.data.local.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.daos.UserWithContactsDao;
import com.example.contactsapp.data.local.database.AppDatabase;

import java.util.List;

public class UserWithContactsRepository {
    private UserWithContactsDao userWithContactsDao;

    public UserWithContactsRepository(Application application) {
        // db call
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        // db access with dao
        userWithContactsDao = appDatabase.userWithContactsDao();
    }

    public LiveData<List<UserWithContacts>> getUserWithContacts(Integer userId) {
        return userWithContactsDao.getUserWithContacts(userId);
    }

}
