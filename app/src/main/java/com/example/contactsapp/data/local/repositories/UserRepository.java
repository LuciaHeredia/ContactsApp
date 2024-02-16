package com.example.contactsapp.data.local.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.daos.UserDao;
import com.example.contactsapp.data.local.database.UserDatabase;
import com.example.contactsapp.data.entities.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        // db call
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        // db access with dao
        userDao = userDatabase.userDao();
        // db data
        allUsers = userDao.getAllUsers();
    }


    /* User */

    public void insertUser(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void deleteUser(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }


    /* UserWithContacts */

    public LiveData<List<UserWithContacts>> getUserWithContacts(Integer userId) {
        return userDao.getUserWithContacts(userId);
    }


    /* Contact */

    public void insertContact(UserWithContacts userWithContacts) {
        new InsertContactAsyncTask(userDao).execute(userWithContacts);
    }


    ////////////////////////// AsyncTasks //////////////////////////

    private static class InsertContactAsyncTask extends AsyncTask<UserWithContacts, Void, Void> {
        private UserDao userDao;

        private InsertContactAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserWithContacts... userWithContacts) {
            Integer userId = userWithContacts[0].getUser().getUserId();
            Contact contact = userWithContacts[0].getContacts().get(0);
            contact.setContactUserId(userId);
            userDao.insertContact(contact);
            return null;
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.insertUser(user[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.updateUser(user[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... user) {
            userDao.deleteUser(user[0]);
            return null;
        }
    }

}
