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

public class MyPeriodicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    { java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MyService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_MUTABLE);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60 * 1000, pintent);
        Log.d("PeriodiskService", "Periodisk startet");
        return super.onStartCommand(intent, flags, startId);



    }
}