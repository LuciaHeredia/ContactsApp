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

        private PopulateDbAsyncTask(AppDatabase db) {
            userDao = db.userDao();
            contactDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // insert user
            User u = new User("admin", "123"); // userId = 1
            userDao.insertUser(u);
            Integer userId = userDao.getUserByUsername(u.getUsername()).getUserId();

            // insert contacts
            Contact c = new Contact("Gin", "Sagiv","male", "0542125478","emailGin@hh.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Ronald", "Johnston","male", "0548825998","RonaldEmail@gg.net");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Yam", "Kar","female", "0542333322","effsdfasdasd@gg.net");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Doron", "Levi","male", "0547667898","doronlevi1999@walla.net");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Haim", "Hadar","male", "0521111111","haim99@walla.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Mccoy", "Reese","male", "0521666666","Reese2000@walla.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Gustavo", "Humphrey","male", "0526566565","HumphreyHumphrey@walla.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Sergio", "Fitzpatrick","male", "0521111111","Fitzpatrick99@walla.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);
            c = new Contact("Whitney", "Swanson","female", "0521111111","WhitneySwanson25@walla.com");
            c.setContactUserId(userId);
            contactDao.insertContact(c);

            return null;
        }
    }


}
