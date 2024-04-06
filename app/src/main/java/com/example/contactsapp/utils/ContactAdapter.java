package com.example.contactsapp.utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.Contact;
import com.example.contactsapp.presentation.models.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private Settings settings;
    private List<Contact> contacts = new ArrayList<>();
    private  OnItemClickListener listener;

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        holder.tv_firstName.setText(currentContact.getFirstName());
        holder.tv_lastName.setText(currentContact.getLastName());
        holder.tv_gender.setText(currentContact.getGender());
        holder.tv_phone.setText(currentContact.getPhone());
        holder.tv_email.setText(currentContact.getEmail());

        /* Settings - show last name */
        if(!settings.isShowLastName()) {
            holder.tv_lastName.setText("");
        }

        /* Settings - show gender */
        if(!settings.isShowGender()) {
            holder.ll_gender.setVisibility(View.GONE);
        } else {
            if(Objects.equals(settings.getShowGenderChoiceStr(), "Text")) {
                holder.ll_gender.setVisibility(View.VISIBLE);
            } else {
                holder.ll_gender.setVisibility(View.GONE);
                /* CardColor based on Gender */
                if(Objects.equals(currentContact.getGender(), "Male")) {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#dbeeff"));
                } else {
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#fff0f7"));
                }
            }
        }

        /* Settings - show phone */
        if(!settings.isShowPhone()) {
            holder.ll_phone.setVisibility(View.GONE);
        } else {
            holder.ll_phone.setVisibility(View.VISIBLE);
        }

        /* Settings - show email */
        if(!settings.isShowEmail()) {
            holder.ll_email.setVisibility(View.GONE);
        } else {
            holder.ll_email.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(Settings settings, List<Contact> contacts) {
        this.settings = settings;

        // sort contacts by first name in alphabetical order
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.getFirstName().toLowerCase().compareTo(rhs.getFirstName().toLowerCase());
            }
        });

        this.contacts = contacts;
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tv_firstName;
        private TextView tv_lastName;
        private TextView tv_gender;
        private TextView tv_phone;
        private TextView tv_email;
        private LinearLayout ll_gender;
        private LinearLayout ll_phone;
        private LinearLayout ll_email;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);

            tv_firstName = itemView.findViewById(R.id.tv_first_name);
            tv_lastName = itemView.findViewById(R.id.tv_last_name);
            tv_gender = itemView.findViewById(R.id.tv_gender_data);
            tv_phone = itemView.findViewById(R.id.tv_phone_data);
            tv_email = itemView.findViewById(R.id.tv_email_data);

            ll_gender = itemView.findViewById(R.id.ll_gender);
            ll_phone = itemView.findViewById(R.id.ll_phone);
            ll_email = itemView.findViewById(R.id.ll_email);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(contacts.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
