package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        initContactFromSharedPref();
        listenerSetup();
        return binding.getRoot();
    }

    private void initContactFromSharedPref() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = prefManager.getContactData();
            currentContact = mapper.readValue(jsonString, Contact.class);
            setContactInfo();
        } catch (JsonProcessingException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void setContactInfo() {
        binding.tvCInfoFirstName.setText(currentContact.getFirstName());
        binding.tvCInfoLastName.setText(currentContact.getLastName());
        binding.tvCInfoGender.setText(currentContact.getGender());
        binding.tvCItemPhone.setText(currentContact.getPhone());
        binding.tvCItemEmail.setText(currentContact.getEmail());
    }

    private void listenerSetup() {
        binding.tvCItemPhone.setOnClickListener(viewCall -> openCallDialer());
        binding.tvCItemEmail.setOnClickListener(viewEmail -> openMailApp());
        binding.editBtn.setOnClickListener(view1 -> editContact());
        binding.deleteBtn.setOnClickListener(view2 -> alertDialogDelete());
    }

    private void openCallDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+binding.tvCItemPhone.getText().toString()));
        startActivity(intent);
    }

    private void openMailApp() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("mailto:"+binding.tvCItemEmail.getText().toString()));
            this.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), Constants.MSG_SOMETHING_WRONG, Toast.LENGTH_SHORT).show();
        }
    }

    private void editContact() {
        prefManager.saveEditContactInfoFlag(true);
        goToEditContact();
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
        prefManager.saveContactData("");
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
