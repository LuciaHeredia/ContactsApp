package com.example.contactsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * userData - saves userData currently logged in. <br/><br/>
 * contactInfo - saves contactInfo to show when clicked in RecyclerView list. <br/><br/>
 * editContactInfo_flag - flag for editing contact's info inside AddContactFragmentLayout. <br/><br/>
 * contactSettings - saves settings on what information to show about the contacts in RecyclerView list. <br/><br/>
 **/
public class PrefManager {

    SharedPreferences sharedPreferences;
    Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
    }

    public void saveUserData(String userObjToString) {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putString("userData", userObjToString);
        userEdit.apply();
    }
    public String getUserData() {
        return sharedPreferences.getString("userData","");
    }
    public boolean isUserLoggedIn() {
        return !getUserData().isEmpty();
    }
    public void userLogout() {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putString("userData", "");
        userEdit.apply();
    }

    public void saveContactData(String contactObjToString) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putString("contactInfo", contactObjToString);
        contactEdit.apply();
    }
    public String getContactData() {
        return sharedPreferences.getString("contactInfo","");
    }

    public void saveEditContactInfoFlag(Boolean bool) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putBoolean("editContactInfo_flag", bool);
        contactEdit.apply();
    }
    public Boolean getEditContactInfoFlag() {
        return sharedPreferences.getBoolean("editContactInfo_flag",false);
    }

    public void saveContactSettings(String settingsObjToString) {
        SharedPreferences.Editor contactEdit = sharedPreferences.edit();
        contactEdit.putString("contactSettings", settingsObjToString);
        contactEdit.apply();
    }
    public String getContactSettings() {
        return sharedPreferences.getString("contactSettings","");
    }

}
