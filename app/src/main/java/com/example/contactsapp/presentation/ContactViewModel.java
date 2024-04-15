package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.repositories.ContactRepository;

public class ContactViewModel extends AndroidViewModel {
    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel

    private ContactRepository repository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
    }

    public void insertContact(UserWithContacts userWithContacts) {
        repository.insertContact(userWithContacts);
    }

    public void updateContact(Contact contact) {
        repository.updateContact(contact);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact);
    }

}
