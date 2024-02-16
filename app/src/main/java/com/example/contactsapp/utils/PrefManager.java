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

}
