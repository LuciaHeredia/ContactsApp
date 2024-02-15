package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.data.local.repositories.UserRepository;
import com.example.contactsapp.data.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<User> userSearchResults;
    private UserRepository repository;
    private LiveData<List<User>> allUsers;

    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userSearchResults = repository.getUserSearchResults();
        allUsers = repository.getAllUsers();
    }

    public void insertUser(UserWithContacts userWithContacts) {
        repository.insertUser(userWithContacts);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(User user) {
        repository.deleteUser(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public MutableLiveData<User> getUserResults() {
        return userSearchResults;
    }

    public void getUser(String name) {
        repository.getUser(name);
    }

    public LiveData<List<UserWithContacts>> getUserWithContacts(String username) {
        return repository.getUserWithContacts(username);
    }

    public void insertContact(UserWithContacts userWithContacts) {
        repository.insertContact(userWithContacts);
    }

}
