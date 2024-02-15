package com.example.contactsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.contactsapp.data.entities.User;

public class PrefManager {

    SharedPreferences sharedPreferences;
    Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_login", Context.MODE_PRIVATE);
    }

    public void saveLoginUserData(User user) {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putString("username", user.getUsername());
        userEdit.apply();
    }

    public String getLoginUserData() {
        return sharedPreferences.getString("username", "");
    }

    public boolean isUserLogin() {
        return !sharedPreferences.getString("username", "").isEmpty();
    }

    public void userLogout() {
        SharedPreferences.Editor userEdit = sharedPreferences.edit();
        userEdit.putString("username", "");
        userEdit.apply();
    }

}
