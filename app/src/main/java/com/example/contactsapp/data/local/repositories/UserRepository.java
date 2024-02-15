package com.example.contactsapp.data.local.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.daos.UserDao;
import com.example.contactsapp.data.local.database.UserDatabase;
import com.example.contactsapp.data.entities.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<User> userSearchResults = new MutableLiveData<>();

    public UserRepository(Application application) {
        // db call
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        // db access with dao
        userDao = userDatabase.userDao();
        // db data
        allUsers = userDao.getAllUsers();
    }

    public void insertUser(UserWithContacts userWithContacts){
        new InsertUserAsyncTask(userDao).execute(userWithContacts);
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

    public void getUser(String username) {
        GetUserAsyncTask task = new GetUserAsyncTask(userDao);
        task.delegate = this;
        task.execute(username);
    }

    public LiveData<List<UserWithContacts>> getUserWithContacts(String username) {
        return userDao.getUserWithContacts(username);
    }

    public void getUserAsyncFinished(User user) {
        userSearchResults.setValue(user);
    }

    public MutableLiveData<User> getUserSearchResults() {
        return userSearchResults;
    }

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
            Integer identifier = userWithContacts[0].user.getUserId();
            Contact contact = userWithContacts[0].contacts.get(0);
            contact.setContactUserId(identifier);
            userDao.insertContact(contact);
            return null;
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserWithContacts, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserWithContacts... params) {
            userDao.insertUser(params[0].user);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... params) {
            userDao.updateUser(params[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... params) {
            userDao.deleteUser(params[0]);
            return null;
        }
    }

    private static class GetUserAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        private UserRepository delegate = null;

        private GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(final String... params) {
            return userDao.getUser(params[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            delegate.getUserAsyncFinished(user);
        }
    }

}
