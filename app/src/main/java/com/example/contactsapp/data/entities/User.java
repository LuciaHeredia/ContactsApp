package com.example.contactsapp.data.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.contactsapp.presentation.models.Settings;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private Integer userId;

    private String username;

    private String password;

    private boolean showLastName;
    private boolean showGender;
    private String showGenderChoiceStr;
    private boolean showPhone;
    private boolean showEmail;

    @Ignore
    private Settings settings;

    /**
     * Constructor
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        initContactSettings();
    }

    public void initContactSettings() {
        this.settings = new Settings();
        this.showLastName = true;
        this.showGender = true;
        this.showGenderChoiceStr = "Text";
        this.showPhone = true;
        this.showEmail = true;
    }

    /**
     * Empty Constructor
     */
    @Ignore
    public User() {
    }


    //* Setters *//

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShowLastName(boolean showLastName) {
        this.settings.setShowLastName(showLastName);
        this.showLastName = showLastName;
    }

    public void setShowGender(boolean showGender) {
        this.settings.setShowGender(showGender);
        this.showGender = showGender;
    }

    public void setShowGenderChoiceStr(String showGenderChoiceStr) {
        this.settings.setShowGenderChoiceStr(showGenderChoiceStr);
        this.showGenderChoiceStr = showGenderChoiceStr;
    }

    public void setShowPhone(boolean showPhone) {
        this.settings.setShowPhone(showPhone);
        this.showPhone = showPhone;
    }

    public void setShowEmail(boolean showEmail) {
        this.settings.setShowEmail(showEmail);
        this.showEmail = showEmail;
    }

    //* Getters *//

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isShowLastName() {
        return showLastName;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public String getShowGenderChoiceStr() {
        return showGenderChoiceStr;
    }

    public boolean isShowPhone() {
        return showPhone;
    }

    public boolean isShowEmail() {
        return showEmail;
    }

    public Settings getSettings() {
        return settings;
    }
}
