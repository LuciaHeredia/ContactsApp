package com.example.contactsapp.data.remote.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.remote.GenderApi;
import com.example.contactsapp.data.remote.response.GenderResponse;
import com.example.contactsapp.data.remote.service.GenderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenderRepository {
    private static  GenderApi api;
    private static GenderRepository genderRepository;
    private MutableLiveData<GenderResponse> genderResponseLiveData;

    public static GenderRepository getInstance(){
        if(genderRepository == null) {
            genderRepository = new GenderRepository();
        }
        return genderRepository;
    }

    private GenderRepository() {
        api = GenderService.getGenderApi();
        genderResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<GenderResponse> getGenderOutput(String name) {
        Call<GenderResponse> responseCall = api.getGenderByName(name);
        responseCall.enqueue(new Callback<GenderResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenderResponse> call, @NonNull Response<GenderResponse> response) {
                genderResponseLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(@NonNull Call<GenderResponse> call, @NonNull Throwable t) {
                genderResponseLiveData.setValue(new GenderResponse("N/A"));
                genderResponseLiveData.postValue(null);
            }
        });
        return genderResponseLiveData;
    }

}
