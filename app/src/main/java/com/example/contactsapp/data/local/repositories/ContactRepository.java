package com.example.contactsapp.data.local.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.daos.ContactDao;
import com.example.contactsapp.data.local.database.AppDatabase;

public class ContactRepository {
    private ContactDao contactDao;

    public ContactRepository(Application application) {
        // db call
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        // db access with dao
        contactDao = appDatabase.contactDao();
    }

    public void insertContact(UserWithContacts userWithContacts) {
        new InsertContactAsyncTask(contactDao).execute(userWithContacts);
    }

    public void updateContact(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void deleteContact(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
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

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.updateContact(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.deleteContact(contacts[0]);
            return null;
        }
    }

}
