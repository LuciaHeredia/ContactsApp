package com.example.contactsapp.data.local.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.local.daos.UserDao;
import com.example.contactsapp.data.local.database.UserDatabase;
import com.example.contactsapp.data.models.User;

public class UserRepository {
    private UserDao userDao;
    private MutableLiveData<User> userSearchResults = new MutableLiveData<>();

    public UserRepository(Application application) {
        // db call
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        // db access with dao
        userDao = userDatabase.userDao();
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public void getUser(String username) {
        GetUserAsyncTask task = new GetUserAsyncTask(userDao);
        task.delegate = this;
        task.execute(username);
    }

    public void getUserAsyncFinished(User user) {
        userSearchResults.setValue(user);
    }

    public MutableLiveData<User> getUserSearchResults() {
        return userSearchResults;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... params) {
            userDao.insert(params[0]);
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
            userDao.update(params[0]);
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
            userDao.delete(params[0]);
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
