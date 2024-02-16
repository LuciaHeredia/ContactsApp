package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.repositories.ContactRepository;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;

    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel
    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
    }

    public void insertContact(UserWithContacts userWithContacts) {
        repository.insertContact(userWithContacts);
    }

}
