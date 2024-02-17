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
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.databinding.FragmentSettingsBinding;
import com.example.contactsapp.presentation.models.Settings;
import com.example.contactsapp.utils.PrefManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private PrefManager prefManager;
    private FragmentSettingsBinding binding;
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
        initContactSettings();
        listenerSetup();
        return binding.getRoot();
    }

    private void initContactSettings() {
        String jsonString = prefManager.getContactSettings();
        ObjectMapper mapper = new ObjectMapper();
        try {
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
        saveToSharedPref();
        Toast.makeText(getActivity(), "Changes saved",Toast.LENGTH_SHORT).show();
        goToContacts();
    }

    private void saveToSharedPref() {
        // save contactSettings
        ObjectMapper mapper = new ObjectMapper();
        try {
            String asString = mapper.writeValueAsString(settings);
            prefManager.saveContactSettings(asString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToContacts() {
        NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_settingsFragment_to_contactsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
