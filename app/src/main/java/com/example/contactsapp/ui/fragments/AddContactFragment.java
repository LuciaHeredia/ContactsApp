package com.example.contactsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.contactsapp.R;
import com.example.contactsapp.databinding.FragmentAddContactBinding;

public class AddContactFragment extends Fragment {
    private FragmentAddContactBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentAddContactBinding.inflate(inflater, container, false);
        //initUsersFromDb();
        listenerSetup();
        return binding.getRoot();
    }

    private void listenerSetup() {
        binding.saveBtn.setOnClickListener(view1 -> addContact());
    }

    private void addContact() {
        // TODO
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
