package com.example.lab1bam;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Counter {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "count")
    public Integer count;

    @ColumnInfo(name = "name")
    public String name;
}
