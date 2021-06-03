package com.example.contacts2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contacts2.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Delete
    void reset(List<Contact> contacts);

    @Update
    void update(Contact contact);

    @Query("SELECT * FROM contact")
    List<Contact> getAll();
}
