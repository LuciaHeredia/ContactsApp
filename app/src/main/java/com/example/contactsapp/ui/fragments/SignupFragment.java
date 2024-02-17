package com.example.contactsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.databinding.FragmentSignupBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private UserViewModel userViewModel;
    private List<User> allUsers;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        initUsersFromDb();
        listenerSetup();
        return binding.getRoot();
    }

    private void listenerSetup() {
        binding.signupButton.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            signupAuth();
        });
    }

    private void initUsersFromDb() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        allUsers = new ArrayList<>();
        userViewModel.getAllUsers().observe(this, dbUsers -> allUsers = dbUsers);
    }

    private void signupAuth() {
        String username = binding.username.getText().toString();
        String password1 = binding.password.getText().toString();
        String password2 = binding.passwordConfirm.getText().toString();
        boolean usernameOK = true;
        boolean passwordsOK = true;

        /* some fields are empty */
        if(username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
            return;
        }
        /* username already exist */
        User foundUser = userViewModel.isUserExist(allUsers, username, 0);
        if (foundUser!=null) {
            binding.username.setError(Constants.MSG_USER_TAKEN);
            usernameOK = false;
        }
        /* password & confirmPassword don't match */
        if(!password1.equals(password2)) {
            binding.password.setError(Constants.MSG_NO_MATCH_PASSWORD);
            binding.passwordConfirm.setError(Constants.MSG_NO_MATCH_PASSWORD);
            passwordsOK = false;
        }

        if(!usernameOK || !passwordsOK) {
            return;
        }

        User newUser = new User(username, password1);
        userViewModel.insertUser(newUser);
        Toast.makeText(getActivity(), Constants.MSG_USER_ADD_SUCCESS,Toast.LENGTH_SHORT).show();
        binding.progressBar.setVisibility(View.INVISIBLE);
        goToLogin();
    }

    private void goToLogin() {
        NavHostFragment.findNavController(SignupFragment.this)
                .navigate(R.id.action_signupFragment_to_loginFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
