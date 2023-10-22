package com.example.s336154_mappe2;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;


public class MyPeriodicService extends Service {

    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

           java.util.Calendar cal = Calendar.getInstance();
           Intent i = new Intent(this, MyService.class);
           PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_MUTABLE);
           AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           cal.setTimeInMillis(System.currentTimeMillis());
           cal.set(Calendar.HOUR_OF_DAY, 6);
           cal.set(Calendar.MINUTE, 0);
           cal.set(Calendar.SECOND, 0);
           if (cal.getTimeInMillis() <= System.currentTimeMillis()) {
               cal.add(Calendar.DAY_OF_YEAR, 1); // Move to tomorrow
           }
           alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);

           Log.d("PeriodicService", "Periodic Service started");

           return super.onStartCommand(intent, flags, startId);
    }
}