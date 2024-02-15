package com.example.contactsapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.contactsapp.data.entities.User;
import com.example.contactsapp.data.entities.UserWithContacts;
import com.example.contactsapp.databinding.FragmentContactsBinding;
import com.example.contactsapp.presentation.UserViewModel;
import com.example.contactsapp.utils.ContactAdapter;
import com.example.contactsapp.utils.PrefManager;

import java.util.List;

public class ContactsFragment extends Fragment {

    private PrefManager prefManager;
    private User user;
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
        initUsersFromDb();
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

    private void recyclerViewSetup() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    private void initUsersFromDb() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserWithContacts(prefManager.getLoginUserData()).observe(this, new Observer<List<UserWithContacts>>() {
            @Override
            public void onChanged(List<UserWithContacts> userWithContacts) {
                adapter.setContacts(userWithContacts.get(0).contacts);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
