package com.example.contactsapp.data.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class UserWithContacts {

    @Embedded
    public User user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "contactId"
    )
    public List<Contact> contacts;

    public UserWithContacts(User user, List<Contact> contacts) {
        this.user = user;
        this.contacts = contacts;
    }

    public void setContact(Contact contact) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
    }

}
