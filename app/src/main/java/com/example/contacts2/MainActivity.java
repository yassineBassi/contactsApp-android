package com.example.contacts2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.contacts2.dao.RoomDB;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoomDB database;
    private List<Contact> dataList;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ContactAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);
        showContactsList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showContactsList();
    }

    public void showContactsList(){
        dataList = database.contactDao().getAll();

        setOnClickListener();

        adapter = new ContactAdapter(this, dataList, listener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new ContactAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                intent.putExtra("contact", dataList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact ?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    database.contactDao().delete(dataList.get(position));
                    showContactsList();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
            }
        };
    }

    public void showContactForm(View v){
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);
    }
}