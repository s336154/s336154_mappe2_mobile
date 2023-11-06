package com.example.s336154_mappe2;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import android.view.View;


import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyPeriodicService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {




        java.util.Calendar cal = Calendar.getInstance();

        Intent iServ = new Intent(this, MyService.class);
        PendingIntent pintentServ = PendingIntent.getService(this, 0, iServ, PendingIntent.FLAG_MUTABLE);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1 * 1000, pintentServ);




           Log.d("PeriodicService", "Periodic Service started");

           return super.onStartCommand(intent, flags, startId);
    }




}