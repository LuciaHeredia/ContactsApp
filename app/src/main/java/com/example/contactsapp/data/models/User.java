package com.example.contactsapp.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private String password;

    private String date;

    //private List<Contact> contacts;


    // Constructor
    public User(String username, String password, String date) {
        this.username = username;
        this.password = password;
        this.date = date;
        //this.contacts = new ArrayList<>(contacts);
    }

    //* Setters *//

    public void setId(int id) {
        this.id = id;
    }

    //* Getters *//

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }

    /*public List<Contact> getContacts() {
        return contacts;
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
