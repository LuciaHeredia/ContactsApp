package com.example.contactsapp.data.remote;

import com.example.contactsapp.data.remote.response.GenderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenderApi {

    @GET(".")
    Call<GenderResponse> getGenderByName(
            @Query("name") String name
    );

}
