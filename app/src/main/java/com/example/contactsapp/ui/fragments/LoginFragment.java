package com.example.contactsapp.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.databinding.FragmentLoginBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private PrefManager prefManager;
    private UserViewModel userViewModel;
    private List<User> allUsers;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        prefManager = new PrefManager(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disableOnBackBtn();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        if(prefManager.isUserLoggedIn()) {
            goToContacts();
        }

        initUsersFromDb();
        underlineTextSetup();
        loginListenerSetup();
        return binding.getRoot();
    }

    private void underlineTextSetup() {
        binding.signupText.setPaintFlags(binding.signupText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.forgotPasswordText.setPaintFlags(binding.forgotPasswordText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void loginListenerSetup() {
        binding.loginButton.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            loginAuth();
        });
        binding.signupText.setOnClickListener(view2 -> goToSignup());
        binding.forgotPasswordText.setOnClickListener(view3 -> changePassword());
    }

    private void disableOnBackBtn() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void initUsersFromDb() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        allUsers = new ArrayList<>();
        userViewModel.getAllUsers().observe(this,
                dbUsers -> allUsers = dbUsers );
    }

    private void loginAuth() {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        /* some fields are empty */
        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        /* user doesn't exist */
        User foundUser = userViewModel.isUserExist(allUsers, username, 0);
        if (foundUser == null) {
            binding.username.setError(Constants.MSG_USER_NOT_FOUND);
            binding.progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        /* password wrong */
        if (!foundUser.getPassword().equals(password)) {
            binding.password.setError(Constants.MSG_WRONG_PASSWORD);
            binding.progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        Toast.makeText(getActivity(), Constants.MSG_LOG_SUCCESS, Toast.LENGTH_SHORT).show();
        saveUserAndSettingsToSharedPref(foundUser);
        binding.progressBar.setVisibility(View.INVISIBLE);
        goToContacts();
    }

    private void changePassword() {
        Dialog changePasswordDialog = new Dialog(getActivity());
        changePasswordDialog.setContentView(R.layout.forgot_password_dialog);
        ProgressBar pgsBar = changePasswordDialog.findViewById(R.id.progressBar);
        Button btnDialog = changePasswordDialog.findViewById(R.id.continue_btn);
        btnDialog.setOnClickListener(v1 -> {
            pgsBar.setVisibility(View.VISIBLE);
            EditText userTextInputDialog = changePasswordDialog.findViewById(R.id.user_input);
            String usernameInput = userTextInputDialog.getText().toString();
            /* some fields are empty */
            if(usernameInput.isEmpty()) {
                userTextInputDialog.setError(Constants.MSG_FIELDS_MANDATORY);
                pgsBar.setVisibility(View.INVISIBLE);
                return;
            }
            /* user doesn't exist */
            User foundUser = userViewModel.isUserExist(allUsers, usernameInput, 0);
            if (foundUser == null) {
                userTextInputDialog.setError(Constants.MSG_NO_USER_NEW_PASS);
                pgsBar.setVisibility(View.INVISIBLE);
                return;
            }

            // changing the dialog appearance
            pgsBar.setVisibility(View.INVISIBLE);
            userTextInputDialog.setText("");
            userTextInputDialog.setHint(R.string.new_password);
            userTextInputDialog.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) });
            userTextInputDialog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signup_password, 0, 0, 0);
            userTextInputDialog.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            btnDialog.setText(R.string.save_txt);
            btnDialog.setOnClickListener(v2 -> {
                pgsBar.setVisibility(View.VISIBLE);
                String newPassword = userTextInputDialog.getText().toString();
                /* some fields are empty */
                if (newPassword.isEmpty()) {
                    userTextInputDialog.setError(Constants.MSG_FIELDS_MANDATORY);
                    pgsBar.setVisibility(View.INVISIBLE);
                    return;
                }
                foundUser.setPassword(newPassword);
                userViewModel.updateUser(foundUser);
                Toast.makeText(getActivity(), Constants.MSG_PASSWORD_CHANGED, Toast.LENGTH_SHORT).show();
                pgsBar.setVisibility(View.INVISIBLE);
                changePasswordDialog.dismiss();
            });

        });
        changePasswordDialog.show();
    }

    private void saveUserAndSettingsToSharedPref(User foundUser) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // save user
            String asString = mapper.writeValueAsString(foundUser);
            prefManager.saveUserData(asString);
            // save settings
            asString = mapper.writeValueAsString(foundUser.getSettings());
            prefManager.saveContactSettings(asString);
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void goToContacts() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_contactsFragment);
    }

    private void goToSignup() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_signupFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}