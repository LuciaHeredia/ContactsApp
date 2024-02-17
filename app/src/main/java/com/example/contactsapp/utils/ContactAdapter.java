package com.example.contactsapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsapp.R;
import com.example.contactsapp.data.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

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
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView tv_firstName;
        private TextView tv_lastName;
        private TextView tv_gender;
        private TextView tv_phone;
        private TextView tv_email;

        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            tv_firstName = itemView.findViewById(R.id.tv_first_name);
            tv_lastName = itemView.findViewById(R.id.tv_last_name);
            tv_gender = itemView.findViewById(R.id.tv_gender_data);
            tv_phone = itemView.findViewById(R.id.tv_phone_data);
            tv_email = itemView.findViewById(R.id.tv_email_data);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(position));
                    }
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
