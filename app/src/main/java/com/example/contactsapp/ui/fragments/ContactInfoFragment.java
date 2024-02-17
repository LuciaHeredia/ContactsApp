package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.databinding.FragmentContactInfoBinding;
import com.example.contactsapp.presentation.ContactViewModel;
import com.example.contactsapp.utils.Constants;
import com.example.contactsapp.utils.PrefManager;

public class ContactInfoFragment extends Fragment {
    private PrefManager prefManager;
    private FragmentContactInfoBinding binding;
    private ContactViewModel contactViewModel;
    private Contact currentContact;
    private  AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        prefManager = new PrefManager(context);
        alertDialogBuilder = new AlertDialog.Builder(context);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentContactInfoBinding.inflate(inflater, container, false);
        initContactFromDb();
        getContactInfoById();
        listenerSetup();
        return binding.getRoot();
    }

    private void initContactFromDb() {
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        observeSetup();
    }

    private void observeSetup() {
        contactViewModel.getContactByIdResults().observe(this,
                contact -> {
                    currentContact = contact;
                    setContactInfo();
                });
    }

    private void getContactInfoById() {
        contactViewModel.getContactById(prefManager.getContactData());
    }

    private void setContactInfo() {
        binding.tvFirstNameData.setText(currentContact.getFirstName());
        binding.tvLastNameData.setText(currentContact.getLastName());
        binding.tvGenderData.setText(currentContact.getGender());
        binding.tvPhoneData.setText(currentContact.getPhone());
        binding.tvEmailData.setText(currentContact.getEmail());
    }

    private void listenerSetup() {
        binding.editBtn.setOnClickListener(view1 -> editContact());
        binding.deleteBtn.setOnClickListener(view1 -> alertDialogDelete());
    }

    private void editContact() {
        goToEditContact();
        // TODO
        //contactViewModel.updateContact(currentContact);
    }

    private void alertDialogDelete(){
        // delete question
        alertDialogBuilder.setMessage(Constants.MSG_ACCEPT_DELETE_CONTACT);
        // yes btn
        alertDialogBuilder.setPositiveButton(Constants.MSG_YES,
                (arg0, arg1) -> {
                    deleteContact();
                    Toast.makeText(getActivity(),Constants.MSG_CONTACT_DELETED,Toast.LENGTH_SHORT).show();
                });
        // no btn
        alertDialogBuilder.setNegativeButton(Constants.MSG_NO,
                (dialog, which) -> alertDialog.dismiss());
        // show dialog
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteContact() {
        contactViewModel.deleteContact(currentContact);
        goToContacts();
    }

    private void goToContacts() {
        NavHostFragment.findNavController(ContactInfoFragment.this)
                .navigate(R.id.action_contactInfoFragment_to_contactsFragment);
    }

    private void goToEditContact() {
        NavHostFragment.findNavController(ContactInfoFragment.this)
                .navigate(R.id.action_contactInfoFragment_to_editContactFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
