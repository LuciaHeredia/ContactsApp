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
import com.example.contactsapp.presentation.ContactViewModel;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddContactFragment extends Fragment {
    private PrefManager prefManager;
    private FragmentAddContactBinding binding;
    private ContactViewModel contactViewModel;
    private Contact currentContact;
    private Boolean editContactFlag;

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
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        editContactFlag = prefManager.getEditContactInfoFlag();
        if(editContactFlag) {
            initContactFromSharedPref();
        }
        listenerSetup();
        return binding.getRoot();
    }

    private void initContactFromSharedPref() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = prefManager.getContactData();
            currentContact = mapper.readValue(jsonString, Contact.class);
            setLayoutForEdit();
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void setLayoutForEdit() {
        binding.titleText.setText(Constants.EDIT_TITLE);
        binding.etFirstName.setText(currentContact.getFirstName());
        binding.etLastName.setText(currentContact.getLastName());
        binding.etPhone.setText(currentContact.getPhone());
        binding.etEmail.setText(currentContact.getEmail());
    }

    private void listenerSetup() {
        binding.saveBtn.setOnClickListener(view1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            addContact();
        });
    }

    private void addContact() {
        String fName = binding.etFirstName.getText().toString();
        String lName = binding.etLastName.getText().toString();
        String phone = binding.etPhone.getText().toString();
        String email = binding.etEmail.getText().toString();
        boolean phoneOK = true;
        boolean emailOK = true;

        /* some fields are empty */
        if(fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(getActivity(), Constants.MSG_FIELDS_MANDATORY,Toast.LENGTH_SHORT).show();
            binding.progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        /* phone must be 10 numbers */
        if (phone.length()<10) {
            binding.etPhone.setError("Must be 10 numbers");
            phoneOK = false;
        } else {
            /* phone must start with 0 */
            if (phone.charAt(0)!='0') {
                binding.etPhone.setError("Must start with 0");
                phoneOK = false;
            }
        }
        /* valid email */
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Email not valid");
            emailOK = false;
        }

        if(!phoneOK || !emailOK) {
            binding.progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        Contact contactToAdd = new Contact(fName, lName, "", phone, email);
        if(editContactFlag) {
            updateContactInDb(contactToAdd);
        } else {
            GetGenderByNameResponse(contactToAdd);
        }
    }

    private void GetGenderByNameResponse(Contact contact) {
        contactViewModel.getGender(contact.getFirstName()).observe(this, genderResponse -> {
            String gender = genderResponse.getGender();
            contact.setGender(gender);
            saveContactToDb(contact);
        });
    }

    private void saveContactToDb(Contact contact) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = prefManager.getUserData();
            User user = mapper.readValue(jsonString, User.class);
            UserWithContacts userWithContacts = new UserWithContacts(user, null);
            userWithContacts.setContact(contact);
            contactViewModel.insertContact(userWithContacts);
            Toast.makeText(getActivity(), Constants.MSG_CONTACT_ADD_SUCCESS,Toast.LENGTH_SHORT).show();
            goToContacts();
        } catch (JsonProcessingException e) {
            binding.progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateContactInDb(Contact contact) {
        contact.setGender(currentContact.getGender());
        contact.setContactId(currentContact.getContactId());
        contact.setContactUserId(currentContact.getContactUserId());
        contactViewModel.updateContact(contact);
        prefManager.saveEditContactInfoFlag(false);
        Toast.makeText(getActivity(), Constants.MSG_CHANGES_SAVED,Toast.LENGTH_SHORT).show();
        goToContacts();
    }

    private void goToContacts() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        NavHostFragment.findNavController(AddContactFragment.this)
                .navigate(R.id.action_addContactFragment_to_contactsFragment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(prefManager.getEditContactInfoFlag()) {
            prefManager.saveEditContactInfoFlag(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
