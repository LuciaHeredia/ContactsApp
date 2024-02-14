package com.example.contactsapp.presentation;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactsapp.data.local.repositories.UserRepository;
import com.example.contactsapp.data.models.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
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

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

}
