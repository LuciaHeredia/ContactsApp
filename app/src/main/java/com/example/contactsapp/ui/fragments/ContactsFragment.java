package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.databinding.FragmentContactsBinding;
import com.example.contactsapp.presentation.UserWithContactsViewModel;
import com.example.contactsapp.presentation.models.Settings;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.ContactAdapter;
import com.example.contactsapp.utils.PrefManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactsFragment extends Fragment implements MenuProvider {

    private PrefManager prefManager;
    private FragmentContactsBinding binding;
    private ContactAdapter adapter;
    private User currentUser;
    private Settings settings;

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

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        getActivity().addMenuProvider(this, getViewLifecycleOwner(), getLifecycle().getCurrentState().RESUMED);
        initUserAndSettingsFromSharedPref();
        recyclerViewSetup();
        initContactsFromDb();
        listenerSetup();
        return binding.getRoot();
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

    private void initUserAndSettingsFromSharedPref() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // init user
            String jsonString = prefManager.getUserData();
            currentUser = mapper.readValue(jsonString, User.class);
            // init settings
            jsonString = prefManager.getContactSettings();
            settings = mapper.readValue(jsonString, Settings.class);
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void listenerSetup() {
        binding.btnContactAdd.setOnClickListener(view1 -> goToAddContact());
    }

    private void recyclerViewSetup() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter();
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::saveContactToSharedPref);
    }

    private void saveContactToSharedPref(Contact contact) {
        // save contact
        ObjectMapper mapper = new ObjectMapper();
        try {
            String asString = mapper.writeValueAsString(contact);
            prefManager.saveContactData(asString);
            goToContactInfo();
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void initContactsFromDb() {
        UserWithContactsViewModel userWithContactsViewModel = new ViewModelProvider(this).get(UserWithContactsViewModel.class);
        userWithContactsViewModel.getUserWithContacts(currentUser.getUserId()).observe(this,
                userWithContacts ->
                        adapter.setContacts(settings, userWithContacts.get(0).getContacts()));
    }

    private void goToContactInfo() {
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_contactsFragment_to_contactInfoFragment);
    }

    private void goToAddContact() {
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_contactsFragment_to_addContactFragment);
    }

    private void goToSettings() {
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_contactsFragment_to_settingsFragment);
    }

    private void goToLogin() {
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_contactsFragment_to_loginFragment);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.contacts_menu, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.contact_settings) {
            goToSettings();
        } else { // Logout
            prefManager.userLogout();
            goToLogin();
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
