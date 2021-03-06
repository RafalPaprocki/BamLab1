package com.example.lab1bam;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CounterDao {
    @Query("Select * From counter")
    List<Counter> getAll();

    @Query("Select * From counter")
    Cursor getCursorAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(Counter... counters);
}
