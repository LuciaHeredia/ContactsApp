package com.example.contactsapp.ui.fragments;

import android.content.Context;
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
import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.databinding.FragmentAddContactBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddContactFragment extends Fragment {
    private PrefManager prefManager;
    private FragmentAddContactBinding binding;
    private UserViewModel userViewModel;

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
        binding = FragmentAddContactBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        listenerSetup();
        return binding.getRoot();
    }

    private void listenerSetup() {
        binding.saveBtn.setOnClickListener(view1 -> addContact());
    }

    private void addContact() {
        String fName = binding.etFirstName.getText().toString();
        String lName = binding.etLastName.getText().toString();
        String gender = "male"; // TODO: from API
        String phone = binding.etPhone.getText().toString();
        String email = binding.etEmail.getText().toString();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if(fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
        } else {
            // TODO: check if fields correct: fName, lName, phone, email
            // TODO: get gender from api
            Contact contact = new Contact(fName, lName, gender, phone, email, date);
            saveSettings(contact);
        }
    }

    private void saveSettings(Contact contact) {
        //UserWithContacts userWithContacts = new UserWithContacts();
        //userWithContacts.setContact(contact);
        //userViewModel.insertContact(userWithContacts);*/
        // message contact saved
        goToContacts();
    }

    private void goToContacts() {
        NavHostFragment.findNavController(AddContactFragment.this)
                .navigate(R.id.action_addContactFragment_to_contactsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
