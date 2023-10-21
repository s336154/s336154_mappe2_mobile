package com.example.s336154_mappe2;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public MyBroadcastReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "I BroadcastReceiver", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, MyService.class);
        Log.d("MinBroadcast","I min boradcast");
        context.startService(i);
    }
}