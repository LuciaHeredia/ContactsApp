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
            entityColumn = "contactUserId"
    )
    public List<Contact> contacts;

    /**
     * Constructor
     */
    public UserWithContacts(User user, List<Contact> contacts) {
        this.user = user;
        this.contacts = contacts;
    }

    //* Setters *//

    public void setContact(Contact contact) {
        if (this.contacts == null) {
            this.contacts = new ArrayList<>();
        }
        this.contacts.add(contact);
    }

    //* Getters *//

    public User getUser() {
        return user;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
