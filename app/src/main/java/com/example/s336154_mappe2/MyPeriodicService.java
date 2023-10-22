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


import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyPeriodicService extends Service {

    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    private String hourSMS, minuteSMS;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        SharedPreferences sharedPref = getDefaultSharedPreferences(this);
        String timeSMS = sharedPref.getString("timeSMS", "");
        Log.d("timeSMS", timeSMS);
        boolean containNum = containsNumbers(timeSMS);

        String[] timeSMS_array = timeSMS.split("[.:\\\\/-]");

        for (String i : timeSMS_array) {
            Log.d("timeSMS_array", "The array contains: " + i);
        }

        if (containNum && timeSMS_array.length == 2) {
            int int_hourSMS = Integer.parseInt(timeSMS_array[0]);
            int int_minSMS = Integer.parseInt(timeSMS_array[1]);

            if (int_hourSMS <= 24 && int_minSMS <= 59) {

                if (timeSMS_array[0].length() == 2) {
                    if (timeSMS_array[0] != "00") {
                        String checkHour1 = String.valueOf(timeSMS_array[0].charAt(0));
                        if (checkHour1 == "0") {
                            hourSMS = String.valueOf(timeSMS_array[0].charAt(1));
                        } else {
                            hourSMS = String.valueOf(timeSMS_array[0]);
                        }
                    } else {
                        hourSMS = "00";
                    }
                } else {
                    hourSMS = String.valueOf(timeSMS_array[0]);
                }
                minuteSMS = String.valueOf(timeSMS_array[1]);
            }
        } else {
            hourSMS = "6";
            minuteSMS = "00";
        }

        int hourSMS_int = Integer.parseInt(hourSMS);
        int minSMS_int = Integer.parseInt(minuteSMS);


        Log.d("timeSMS_hour", "Hour set is: " + hourSMS);
        Log.d("timeSMS_minute", "Minute set is: " + minuteSMS);

           java.util.Calendar cal = Calendar.getInstance();
           Intent i = new Intent(this, MyService.class);
           PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_MUTABLE);
           AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           cal.setTimeInMillis(System.currentTimeMillis());
           cal.set(Calendar.HOUR_OF_DAY, hourSMS_int);
           cal.set(Calendar.MINUTE, minSMS_int);
           cal.set(Calendar.SECOND, 0);
           if (cal.getTimeInMillis() <= System.currentTimeMillis()) {
               cal.add(Calendar.DAY_OF_YEAR, 1); // Move to tomorrow
           }
           alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);

           Log.d("PeriodicService", "Periodic Service started");

           return super.onStartCommand(intent, flags, startId);
    }

    public static boolean containsNumbers(String input) {
        // Define a regular expression pattern to match numbers
        Pattern pattern = Pattern.compile(".*\\d+.*");

        // Use a Matcher to check if the input String contains numbers
        Matcher matcher = pattern.matcher(input);

        // Return true if numbers are found, false otherwise
        return matcher.matches();
    }
}