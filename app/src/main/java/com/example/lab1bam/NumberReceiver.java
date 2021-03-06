package com.example.lab1bam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class NumberReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        String name = arg1.getStringExtra("Name");
        Integer counterValue = arg1.getIntExtra("CounterValue", 0 );
        Log.d("Broadcast receiver", "Hey, " + name + ", counter value for you is " + counterValue.toString());
        Thread thread = new Thread(() -> {
            AppDatabase db = Room.databaseBuilder(arg0, AppDatabase.class, "counterDatabase.sql").build();
            try {
                CounterDao counterDao = db.counterDao();
                Counter counter2 = new Counter();

                counter2.count = counterValue;
                counter2.name = name;
                counterDao.insertUsers(counter2);
            } finally {
                db.close();
            }
        });

        thread.start();
    }
}
