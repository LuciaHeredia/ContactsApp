package com.example.contactsapp.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.databinding.FragmentLoginBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private PrefManager prefManager;
    private UserViewModel userViewModel;
    private String ObserverType;
    private Dialog changePasswordDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        prefManager = new PrefManager(context);
        if(prefManager.isUserLogin()){
            goToContacts();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observerSetup();
        disableOnBackBtn();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        underlineTextSetup();
        loginListenerSetup();
        return binding.getRoot();
    }

    private void underlineTextSetup() {
        binding.signupText.setPaintFlags(binding.signupText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.forgotPasswordText.setPaintFlags(binding.forgotPasswordText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void loginListenerSetup() {
        binding.loginButton.setOnClickListener(view1 -> loginAuth());
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

    private void observerSetup() {
        userViewModel.getUserResults().observe(this, foundUser -> {
            if (ObserverType.equals(String.valueOf(R.string.login))) {
                if (foundUser == null) {
                    Toast.makeText(getActivity(), Constants.MSG_USER_NOT_FOUND, Toast.LENGTH_SHORT).show();
                } else {
                    String password = binding.password.getText().toString();
                    if (!foundUser.getPassword().equals(password)) {
                        Toast.makeText(getActivity(), Constants.MSG_WRONG_PASSWORD, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), Constants.MSG_LOG_SUCCESS, Toast.LENGTH_LONG).show();
                        prefManager.saveLoginUserData(foundUser);
                        goToContacts();
                    }
                }
            } else {
                if (foundUser == null) {
                    Toast.makeText(getActivity(), Constants.MSG_NO_USER_NEW_PASS, Toast.LENGTH_SHORT).show();
                } else {
                    Button btnDialog = changePasswordDialog.findViewById(R.id.continue_btn);
                    EditText userTextInputDialog = changePasswordDialog.findViewById(R.id.user_input);

                    // changing the dialog appearance
                    userTextInputDialog.setText("");
                    userTextInputDialog.setHint(R.string.new_password);
                    userTextInputDialog.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signup_password, 0, 0, 0);
                    btnDialog.setText(R.string.save_txt);

                    btnDialog.setOnClickListener(v2 -> {
                        String newPassword = userTextInputDialog.getText().toString();
                        if (newPassword.equals("")) {
                            Toast.makeText(getActivity(), Constants.MSG_ENTER_NEW_PASSWORD, Toast.LENGTH_SHORT).show();
                        } else {
                            foundUser.setPassword(newPassword);
                            userViewModel.updateUser(foundUser);
                            Toast.makeText(getActivity(), Constants.MSG_PASSWORD_CHANGED, Toast.LENGTH_LONG).show();
                            changePasswordDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void loginAuth() {
        String username = binding.username.getText().toString();
        String password = binding.password.getText().toString();

        if(username.equals("") || password.equals("")) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
        } else {
            ObserverType = String.valueOf(R.string.login);
            userViewModel.getUser(username);
        }
    }

    private void changePassword() {
        changePasswordDialog = new Dialog(getActivity());
        changePasswordDialog.setContentView(R.layout.forgot_password_dialog);
        Button btnDialog = changePasswordDialog.findViewById(R.id.continue_btn);
        btnDialog.setOnClickListener(v1 -> {
            EditText userTextInputDialog = changePasswordDialog.findViewById(R.id.user_input);
            String usernameInput = userTextInputDialog.getText().toString();
            if(usernameInput.equals("")) {
                Toast.makeText(getActivity(), Constants.MSG_ENTER_USERNAME,Toast.LENGTH_SHORT).show();
            } else {
                ObserverType = String.valueOf(R.string.change_password);
                userViewModel.getUser(usernameInput);
            }
        });
        changePasswordDialog.show();
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