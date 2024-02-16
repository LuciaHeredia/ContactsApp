package com.example.contactsapp.data.local.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.daos.ContactDao;
import com.example.contactsapp.data.local.database.UserDatabase;

public class ContactRepository {
    private ContactDao contactDao;

    public ContactRepository(Application application) {
        // db call
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        // db access with dao
        contactDao = userDatabase.contactDao();
    }

    public void insertContact(UserWithContacts userWithContacts) {
        new InsertContactAsyncTask(contactDao).execute(userWithContacts);
    }

    ////////////////////////// AsyncTasks //////////////////////////

    private static class InsertContactAsyncTask extends AsyncTask<UserWithContacts, Void, Void> {
        private ContactDao contactDao;

        private InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(UserWithContacts... userWithContacts) {
            Integer userId = userWithContacts[0].getUser().getUserId();
            Contact contact = userWithContacts[0].getContacts().get(0);
            contact.setContactUserId(userId);
            contactDao.insertContact(contact);
            return null;
        }
    }

}
