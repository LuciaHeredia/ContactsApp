package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.contactsapp.databinding.FragmentContactInfoBinding;
import com.example.contactsapp.utils.PrefManager;

public class ContactInfoFragment extends Fragment {
    private PrefManager prefManager;
    private FragmentContactInfoBinding binding;

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
        binding = FragmentContactInfoBinding.inflate(inflater, container, false);
        showContactInfo();
        listenerSetup();
        return binding.getRoot();
    }

    private void showContactInfo() {
        //prefManager.getContactData();
    }

    private void listenerSetup() {
        binding.editBtn.setOnClickListener(view1 -> editContact());
        binding.deleteBtn.setOnClickListener(view1 -> deleteContact());
    }

    private void editContact() {
    }

    private void deleteContact() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
