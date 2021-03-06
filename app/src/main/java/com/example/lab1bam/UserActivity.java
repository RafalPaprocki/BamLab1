package com.example.lab1bam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class UserActivity extends AppCompatActivity {
    private AppDatabase db;
    private CounterDao userDao;
    private NumberReceiver broadcast = new NumberReceiver();
    private IntentFilter filter =
            new IntentFilter("notification.counter");

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcast, filter);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(broadcast, filter);
        setContentView(R.layout.activity_user);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "counterDatabase.sql").build();
        userDao = db.counterDao();

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    public void startCounter(View view) {
        Intent intent = new Intent(this, CounterService.class);
        EditText editText = (EditText) findViewById(R.id.personName);
        String message = editText.getText().toString();
        intent.putExtra("userName", message);

        this.startService(intent);
    }

    public void stopCounter(View view){
        Intent intent = new Intent(this, CounterService.class);
        this.stopService(intent);
    }

    public void getCounters(View view){
        Thread thread = new Thread(() -> {
            try {
                List<Counter> users = userDao.getAll();
                for (Counter user : users){
                    Log.d("Database read", "Name: " + user.name + " Counter: " + user.count);
                }
            }catch(Exception e){
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(broadcast);
        db.close();
        super.onDestroy();
    }

}