package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.databinding.FragmentSettingsBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.presentation.models.Settings;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private PrefManager prefManager;
    private FragmentSettingsBinding binding;
    private UserViewModel userViewModel;
    private Settings settings;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        prefManager = new PrefManager(context);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        initContactSettings();
        listenerSetup();
        return binding.getRoot();
    }

    private void initContactSettings() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = prefManager.getContactSettings();
            settings = mapper.readValue(jsonString, Settings.class);
            setContactSettings();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void setContactSettings() {
        binding.lastNameSwitch.setChecked(settings.isShowLastName());
        binding.genderSwitch.setChecked(settings.isShowGender());
        binding.phoneSwitch.setChecked(settings.isShowPhone());
        binding.emailSwitch.setChecked(settings.isShowEmail());
    }

    private void listenerSetup() {
        // switches
        binding.lastNameSwitch.setOnCheckedChangeListener(this);
        binding.genderSwitch.setOnCheckedChangeListener(this);
        binding.phoneSwitch.setOnCheckedChangeListener(this);
        binding.emailSwitch.setOnCheckedChangeListener(this);
        // save btn
        binding.saveBtn.setOnClickListener(view1 -> saveContactSettings());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton.getId()==binding.lastNameSwitch.getId()) {
            settings.setShowLastName(isChecked);
        } else if(compoundButton.getId()==binding.genderSwitch.getId()) {
            settings.setShowGender(isChecked);
        } else if(compoundButton.getId()==binding.phoneSwitch.getId()) {
            settings.setShowPhone(isChecked);
        } else {
            settings.setShowEmail(isChecked);
        }
    }

    private void saveContactSettings() {
        saveUserAndSettingsToDB();
    }

    public void saveUserAndSettingsToDB() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = prefManager.getUserData();
            User user = mapper.readValue(jsonString, User.class);
            user.setShowLastName(settings.isShowLastName());
            user.setShowGender(settings.isShowGender());
            user.setShowPhone(settings.isShowPhone());
            user.setShowEmail(settings.isShowEmail());
            userViewModel.updateUser(user);
            saveUserAndSettingsToSharedPref(user);
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserAndSettingsToSharedPref(User user) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String asString = mapper.writeValueAsString(settings);
            prefManager.saveContactSettings(asString);
            asString = mapper.writeValueAsString(user);
            prefManager.saveUserData(asString);
            Toast.makeText(getActivity(), Constants.MSG_CHANGES_SAVED,Toast.LENGTH_SHORT).show();
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
