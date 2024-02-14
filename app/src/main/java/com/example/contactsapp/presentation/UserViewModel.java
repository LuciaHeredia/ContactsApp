package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.local.repositories.UserRepository;
import com.example.contactsapp.data.models.User;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<User> userSearchResults;
    private UserRepository repository;

    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userSearchResults = repository.getUserSearchResults();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public MutableLiveData<User> getUserResults() {
        return userSearchResults;
    }

    public void getUser(String name) {
        repository.getUser(name);
    }

}
