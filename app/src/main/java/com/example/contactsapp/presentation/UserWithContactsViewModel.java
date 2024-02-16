package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.repositories.UserWithContactsRepository;

import java.util.List;

public class UserWithContactsViewModel extends AndroidViewModel {

    private UserWithContactsRepository userWithContactsRepository;

    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel
    public UserWithContactsViewModel(@NonNull Application application) {
        super(application);
        userWithContactsRepository = new UserWithContactsRepository(application);
    }

    public LiveData<List<UserWithContacts>> getUserWithContacts(Integer userId) {
        return userWithContactsRepository.getUserWithContacts(userId);
    }

}
