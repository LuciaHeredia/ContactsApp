package com.example.contactsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.contactsapp.databinding.FragmentContactInfoBinding;

public class ContactInfoFragment extends Fragment {
    private FragmentContactInfoBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentContactInfoBinding.inflate(inflater, container, false);
        listenerSetup();
        return binding.getRoot();
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
