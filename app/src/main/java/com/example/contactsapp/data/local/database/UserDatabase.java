package com.example.contactsapp.data.local.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.local.daos.ContactDao;
import com.example.contactsapp.data.local.daos.UserDao;
import com.example.contactsapp.data.entities.User;

@Database(entities = {User.class, Contact.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ContactDao contactDao();

    // Singleton
    private static UserDatabase instance;

    // synchronized -> only one thread at a time
    public static synchronized UserDatabase getInstance(Context context) {
        if(instance == null) {
            // creating new user database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }



    //* Populate DB Manually *//

    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private UserDao userDao;
        private ContactDao contactDao;

        private PopulateDbAsyncTask(UserDatabase db) {
            userDao = db.userDao();
            contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            User u = new User("adam", "123", "13-02-2024"); // userId = 1
            userDao.insertUser(u);

            // insert contacts
            Contact c = new Contact("gin", "segev","male", "444","email@ggg","11/02/2023");
            Contact c2 = new Contact("ron", "sss","male", "444","email@ggg","11/02/2023");

            // i have the userId from shared preferences ////
            Integer userId = userDao.getUserByUsername(u.getUsername()).getUserId();
            c.setContactUserId(userId);
            c2.setContactUserId(userId);
            contactDao.insertContact(c);
            contactDao.insertContact(c2);

            return null;
        }
    }


}
