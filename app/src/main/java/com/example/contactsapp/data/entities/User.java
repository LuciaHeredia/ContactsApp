package com.example.contactsapp.data.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private Integer userId;

    private String username;

    private String password;

    /**
     * Constructor
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * A Mock User
     * @param userId from an already existing user.
     */
    @Ignore
    public User(Integer userId) {
        this.userId = userId;
    }

    //* Setters *//

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
