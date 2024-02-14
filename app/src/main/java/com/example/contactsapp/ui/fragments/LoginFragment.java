package com.example.contactsapp.ui.fragments;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.data.models.User;
import com.example.contactsapp.databinding.FragmentLoginBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UserViewModel userViewModel;
    private List<User> users = new ArrayList<>();

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

        initUsersFromDb();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // underline text
        binding.signupText.setPaintFlags(binding.signupText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.forgotPasswordText.setPaintFlags(binding.forgotPasswordText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        // click listeners
        binding.loginButton.setOnClickListener(view1 -> loginAuth());
        binding.signupText.setOnClickListener(view2 -> goToSignup());
        binding.forgotPasswordText.setOnClickListener(view3 -> forgotPassword());
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
        userViewModel.getAllUsers().observe(this, dbUsers -> users = dbUsers);
    }

    private void loginAuth() {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        // TODO: TEST: print all users
        for(User user: users) {
            Log.v("TAG",user.toString());
        }

        if(username.equals("") || password.equals("")) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
        } else {
            User foundUser = isUserExist(username);
            if(foundUser==null) {
                Toast.makeText(getActivity(), Constants.MSG_USER_NOT_FOUND,Toast.LENGTH_SHORT).show();
            } else {
                if(!foundUser.getPassword().equals(password)) {
                    Toast.makeText(getActivity(), Constants.MSG_WRONG_PASSWORD,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), Constants.MSG_LOG_SUCCESS,Toast.LENGTH_LONG).show();
                    goToContacts();
                }
            }
        }
    }

    @Nullable
    private User isUserExist(String username) {
        if(users != null) {
            for(User user: users) {
                if(user.getUsername().equals(username)) {
                    return user;
                }
            }
        }
        return null;
    }

    private void goToContacts() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_contactsFragment);
    }

    private void goToSignup() {
        NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_loginFragment_to_signupFragment);
    }

    private void forgotPassword() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.forgot_password_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.continue_btn);
        dialogButton.setOnClickListener(v1 -> {
            EditText userTextInput = (EditText) dialog.findViewById(R.id.user_input);
            String usernameInput = userTextInput.getText().toString();
            if(usernameInput.equals("")) {
                Toast.makeText(getActivity(), Constants.MSG_ENTER_USERNAME,Toast.LENGTH_SHORT).show();
            } else {
                User foundUser = isUserExist(usernameInput);
                if (foundUser == null) {
                    Toast.makeText(getActivity(), Constants.MSG_NO_USER_NEW_PASS, Toast.LENGTH_SHORT).show();
                } else {
                    // changing the dialog appearance
                    userTextInput.setText("");
                    userTextInput.setHint(R.string.new_password);
                    userTextInput.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signup_password, 0, 0, 0);
                    dialogButton.setText(R.string.save_txt);

                    dialogButton.setOnClickListener(v2 -> {
                        String newPassword = userTextInput.getText().toString();
                        if (newPassword.equals("")) {
                            Toast.makeText(getActivity(), Constants.MSG_ENTER_NEW_PASSWORD, Toast.LENGTH_SHORT).show();
                        } else {
                            foundUser.setPassword(newPassword);
                            userViewModel.update(foundUser);
                            Toast.makeText(getActivity(), Constants.MSG_PASSWORD_CHANGED, Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}