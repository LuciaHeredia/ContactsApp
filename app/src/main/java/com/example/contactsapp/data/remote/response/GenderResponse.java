package com.example.contactsapp.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenderResponse {

    @SerializedName("gender")
    @Expose()
    private String gender;

    public GenderResponse(String gender) {
        this.gender = gender;
    }

    public String getGender(){
        return gender;
    }

}
