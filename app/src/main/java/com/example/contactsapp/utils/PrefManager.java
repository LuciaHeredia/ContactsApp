package com.example.contactsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.User;

/**
 * userId_pref - saves userId currently logged in. <br/><br/>
 * contactId_pref - saves contactId to show when clicked in RecyclerView list. <br/><br/>
 * contactSettings_pref - saves settings on what information to show about the contacts in RecyclerView list. <br/><br/>
 * saveUserToDb_pref - update user with new settings and save in db. <br/><br/>
 **/
public class PrefManager {

    SharedPreferences sharedPreferences;
    Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
    }

    public void saveLoginUserData(User user) {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putInt("userId_pref", user.getUserId());
        userEdit.apply();
    }

    public Integer getLoginUserData() {
        return sharedPreferences.getInt("userId_pref",0);
    }

    public boolean isUserLogin() {
        return sharedPreferences.getInt("userId_pref", 0) > 0;
    }

    public void userLogout() {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putInt("userId_pref", 0);
        userEdit.apply();
    }

    public void saveContactData(Contact contact) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putInt("contactId_pref", contact.getContactId());
        contactEdit.apply();
    }

    public Integer getContactData() {
        return sharedPreferences.getInt("contactId_pref",0);
    }

    public void saveContactSettings(String settingsObjToString) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putString("contactSettings_pref", settingsObjToString);
        contactEdit.apply();
    }

    public String getContactSettings() {
        return sharedPreferences.getString("contactSettings_pref","");
    }

    public void saveUserToDb(Integer userId) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putInt("saveUserToDb_pref", userId);
        contactEdit.apply();
    }

    public Integer getSaveUserToDb() {
        return sharedPreferences.getInt("saveUserToDb_pref",0);
    }

}
