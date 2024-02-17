package com.example.contactsapp.presentation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contactsapp.data.local.repositories.UserRepository;
import com.example.contactsapp.data.entities.User;

import java.util.List;
import java.util.Objects;

public class UserViewModel extends AndroidViewModel {
    // AndroidViewModel(extends ViewModel) - for using context inside the ViewModel

    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private MutableLiveData<User> userSearchResults;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        userSearchResults = repository.getUserByIdSearchResults();
    }

    public void insertUser(User user) {
        repository.insertUser(user);
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

    public MutableLiveData<User> getUserByIdResults() {
        return userSearchResults;
    }
    public void getUserById(Integer userId) {
        repository.getUserById(userId);
    }

    public User isUserExist(List<User> foundUsers, String username, Integer userId) {
        if(foundUsers != null) {
            for(User user: foundUsers) {
                if(userId==0) { // search by username
                    if(user.getUsername().equals(username))
                        return user;
                } else { // search by userId
                    if(user.getUserId().equals(userId))
                        return user;
                }
            }
        }
        return null;
    }

}
