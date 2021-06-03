package com.example.contacts2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.contacts2.dao.RoomDB;

public class AddContact extends AppCompatActivity {

    private EditText firstName, lastName, job, email, phone;

    private RoomDB database;

    Contact contact = new Contact();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        setTitle("Add Contact");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        job = findViewById(R.id.job);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        if(getIntent().getExtras() != null){
            contact = (Contact) getIntent().getExtras().get("contact");
            setContact();
        }

        database = RoomDB.getInstance(this);
    }

    private void setContact() {
        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        job.setText(contact.getJob());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_contact_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean saveContact(MenuItem item){

        contact.setFirstName(firstName.getText().toString());
        contact.setLastName(lastName.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setJob(job.getText().toString());
        contact.setPhone(phone.getText().toString());

        if(contact.getId() != 0) {
            database.contactDao().update(contact);
        }
        else {
            database.contactDao().insert(contact);
        }

        clearForm(null);

        finish();
        return true;
    }


    public void clearForm(MenuItem item){
        firstName.setText("");
        lastName.setText("");
        job.setText("");
        email.setText("");
        phone.setText("");
    }
}