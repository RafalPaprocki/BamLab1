package com.example.lab1bam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

public class CounterService extends Service {
    private boolean isRunning = false;
    public CounterService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        String userName = intent.getStringExtra("userName");
        Thread thread = new Thread(() -> {
            try {
                int counter = 0;
                while (isRunning == true) {
                    counter++;
                    Log.d("CounterService", "Count: " + Integer.toString(counter));
                    Thread.sleep(1000);
                }
                Intent intent2 = new Intent();
                intent2.setAction("notification.counter");
                intent2.putExtra("Name", userName);
                intent2.putExtra("CounterValue", counter);
                sendBroadcast(intent2);
            }catch(Exception e){
            }
        });
        thread.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {

        isRunning = false;
    }
}