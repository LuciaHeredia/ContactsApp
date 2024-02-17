package com.example.contactsapp.data.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table",
        foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "userId", childColumns = "contactUserId",
        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private Integer contactId;

    private Integer contactUserId;

    private String firstName;

    private String lastName;

    private String gender;

    private String phone;

    private String email;

    /**
     * Constructor
     */
    public Contact(String firstName, String lastName, String gender, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }

    //* Setters *//

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public void setContactUserId(Integer contactUserId) {
        this.contactUserId = contactUserId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //* Getters *//

    public Integer getContactId() {
        return contactId;
    }

    public Integer getContactUserId() {
        return contactUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
