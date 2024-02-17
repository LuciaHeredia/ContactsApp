package com.example.contactsapp.data.remote.service;

import com.example.contactsapp.data.remote.GenderApi;
import com.example.contactsapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenderService {

    // Service Generator
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    // Singleton Retrofit
    private static Retrofit retrofit = retrofitBuilder.build();

    private static GenderApi genderApi = retrofit.create(GenderApi.class);

    public static GenderApi getGenderApi() {
        return genderApi;
    }

}
