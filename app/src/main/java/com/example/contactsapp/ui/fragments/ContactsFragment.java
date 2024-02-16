package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contactsapp.R;
import com.example.contactsapp.databinding.FragmentContactsBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.ContactAdapter;
import com.example.contactsapp.utils.PrefManager;

public class ContactsFragment extends Fragment {

    private PrefManager prefManager;
    private FragmentContactsBinding binding;
    private UserViewModel userViewModel;
    private ContactAdapter adapter;

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

    private void listenerSetup() {
        binding.btnContactAdd.setOnClickListener(view1 -> goToAddContact());
    }

    private void recyclerViewSetup() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    private void initContactsFromDb() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserWithContacts(prefManager.getLoginUserData()).observe(this,
                userWithContacts -> adapter.setContacts(userWithContacts.get(0).getContacts()));
    }

    private void goToAddContact() {
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_contactsFragment_to_addContactFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
