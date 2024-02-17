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
import com.example.contactsapp.data.local.daos.UserWithContactsDao;

@Database(entities = {User.class, Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ContactDao contactDao();
    public abstract UserWithContactsDao userWithContactsDao();

    // Singleton
    private static AppDatabase instance;

    // synchronized -> only one thread at a time
    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            // creating new app database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
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
        private UserWithContactsDao userWithContactsDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            userDao = db.userDao();
            contactDao = db.contactDao();
            userWithContactsDao = db.userWithContactsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // insert user
            User u = new User("adam", "123"); // userId = 1
            userDao.insertUser(u);

            // insert contacts
            Contact c1 = new Contact("gin", "segev","male", "0542125478","email@hh.com");
            Contact c2 = new Contact("ron", "sss","male", "0548825998","email@gg.net");

            // i have the userId from shared preferences
            Integer userId = userDao.getUserByUsername(u.getUsername()).getUserId();
            c1.setContactUserId(userId);
            c2.setContactUserId(userId);
            contactDao.insertContact(c1);
            contactDao.insertContact(c2);

            return null;
        }
    }


}
