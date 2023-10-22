package com.example.s336154_mappe2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {
    private final Context context;
    private final List<Contact> contacts;
    private ContactAdapter contactAdapter;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context context, List<Contact> contacts) {
        super();
        this.context = context;
        this.contacts = contacts;
        inflater= LayoutInflater.from(context);
    }

    public Contact getItem(int position) {
        if (position >= 0 && position < contacts.size()) {
            return contacts.get(position);
        }
        return null; // Handle out-of-bounds positions
    }

    public void add(Contact contact) {
        contacts.clear();
        contacts.add(contact);
        notifyDataSetChanged();
    }

    public void remove(Contact contact) {
        contacts.clear();
        contacts.remove(contact);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contacts.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.list_contact_layout, null);

     //   inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  View rowView = inflater.inflate(R.layout.list_contact_layout, parent, false);

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
        TextView nameView = (TextView) convertView.findViewById(R.id.nameView);
        TextView phoneView = (TextView) convertView.findViewById(R.id.phoneView);

        // Set the icon image (you can load it from a resource or URL)
        iconImageView.setImageResource(R.drawable.img_3);

        Contact contact = contacts.get(position);

        Log.d("CustomAdapter", "Name: " + contact.getName());
        Log.d("CustomAdapter", "Phone Number: " + contact.getPhone());

        // Set the text for the item
        nameView.setText(contact.getName());
        phoneView.setText(contact.getPhone());

        return convertView;
    }
}
