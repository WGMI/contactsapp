package com.example.mugandaimo.mycontacts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    private DBHandler dbHandler;

    private ListView contactsList;
    private EditText searchText;
    private Button search,add;

    private List<Contact> contacts = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        dbHandler = new DBHandler(context);

        Log.d("count_c", "onCreate: " + dbHandler.getContactCount());

        contactsList = (ListView)findViewById(R.id.contact_list);
        searchText = (EditText)findViewById(R.id.search_text);
        search = (Button)findViewById(R.id.search);
        add = (Button)findViewById(R.id.add);

        loadContacts();

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Contact contact = contacts.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(contact.getName())
                        .setMessage(
                                contact.getNumber()
                                        + "\n" + contact.getEmail()
                                        + "\n" + contact.getOrganization()
                                        + "\n" + contact.getRelationship()
                        )
                        .show();
            }
        });

        contactsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contacts.get(i);
                dbHandler.deleteContact(contact);
                loadContacts();
                return true;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddContact.class);
                startActivity(intent);
            }
        });
    }

    private void loadContacts() {
        contacts.clear();
        contacts = dbHandler.getAllContacts();

        String[] namesArray = new String[contacts.size()];

        for(int x=0;x<contacts.size();x++){
            namesArray[x] = contacts.get(x).getName();
            Log.d("array_names", "onCreate: " + namesArray[x] + contacts.size());
        }

        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,namesArray);
        contactsList.setAdapter(adapter);
    }
}
