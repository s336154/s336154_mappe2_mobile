package com.example.s336154_mappe2;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    String CHANNEL_ID =  "MinKanal";
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().
                replace(android.R.id.content, new SettingsFragment()).commit();


        BroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);

        createNotificationChannel();
    }





    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String descripUon = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        channel.setDescription(descripUon);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}