package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.repositories.ContactRepository;
import com.example.contactsapp.data.remote.repositories.GenderRepository;
import com.example.contactsapp.data.remote.response.GenderResponse;

public class ContactViewModel extends AndroidViewModel {
    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel

    private ContactRepository repository;
    private GenderRepository genderRepository;
    private MutableLiveData<GenderResponse> genderResponseLiveData = new MutableLiveData<>();

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        genderRepository = GenderRepository.getInstance();
    }

    public void insertContact(UserWithContacts userWithContacts) {
        repository.insertContact(userWithContacts);
    }

    private MutableLiveData<GenderResponse> loadGender(String name) {
        return genderRepository.getListOfMoviesOutputs(name);
    }
    public MutableLiveData<GenderResponse> getGender(String name) {
        genderResponseLiveData = loadGender(name);
        return genderResponseLiveData;
    }

}
