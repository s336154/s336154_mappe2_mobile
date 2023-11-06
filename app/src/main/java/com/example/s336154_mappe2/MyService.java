package com.example.s336154_mappe2;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

public class MyService extends Service {

    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    List<Long> meetingsIDs;

    private String hourSMS, minuteSMS;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "Service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        java.util.Calendar cal = Calendar.getInstance();

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String timeSMS = sharedPref.getString("timeSMS", "");
        Log.d("timeSMS", timeSMS);
        boolean containNum = containsNumbers(timeSMS);

        String[] timeSMS_array = timeSMS.split("[.:\\\\/-]");

        if (containNum && timeSMS_array.length == 2) {
            int int_hourSMS = Integer.parseInt(timeSMS_array[0]);
            int int_minSMS = Integer.parseInt(timeSMS_array[1]);

            if (int_hourSMS <= 24 && int_minSMS <= 59) {
                hourSMS = String.format("%02d", int_hourSMS);
                minuteSMS = String.format("%02d", int_minSMS);
            }
        } else {
            hourSMS = "06";
            minuteSMS = "00";
        }

        int hourSMS_int = Integer.parseInt(hourSMS);
        int minSMS_int = Integer.parseInt(minuteSMS);

        Log.d("timeSMS_hour", "Hour set is: " + hourSMS);
        Log.d("timeSMS_minute", "Minute set is: " + minuteSMS);

        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hourSMS_int);
        cal.set(Calendar.MINUTE, minSMS_int);
        cal.set(Calendar.SECOND, 0);

        LocalTime currentTime = LocalTime.now();

        if (currentTime.getHour() == hourSMS_int && currentTime.getMinute() == minSMS_int) {
            boolean checkedSMS = sharedPref.getBoolean("activateSMS", true);
            boolean prefNotif = sharedPref.getBoolean("activateNotification", true);
            String Str_checkedSMS = String.valueOf(checkedSMS);
            Log.d("checkedSMS", Str_checkedSMS);

            meetingsIDs = meetingAdapter.getMeetingIdsWithTodayDate(this);

            for (long  IDS: meetingsIDs) {
                Log.d("Matching IDs", "Meeting with ID: " + String.valueOf(IDS) + " takes place today.");
            }

            if(prefNotif) {

                Intent iNot = new Intent(this, MainActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, iNot, PendingIntent.FLAG_MUTABLE);
                Notification notifyAvtale = new NotificationCompat.Builder(this, "MinKanal")
                        .setContentTitle("Avtale")
                        .setContentText("Du har avtale i dag.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pIntent).build();
                notifyAvtale.flags |= Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(88, notifyAvtale);

            }
            Log.d("MyService", "In MyService");




            if (checkedSMS) {
                sendMessage();
            }


        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessage() {
        String nameSMS = null;
        String phoneSMS = null;
        String messageSMS = null;
        long idSMS = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (meetingsIDs.size() > 0) {
            for (long id : meetingsIDs) {


                idSMS = meetingAdapter.getContactIdForMeeting(id);
                nameSMS = contactAdapter.getContactName(idSMS);
                phoneSMS = contactAdapter.getContactPhone(idSMS);

                String defaultSMS = "Hei. Dette er en påminnelse på avtalen som vi har i dag. Vi ses!";
                String message = sharedPreferences.getString("textSMS", defaultSMS);

                Log.d("contactSMS", "Contact name is " + nameSMS);
                Log.d("contactSMS", "Phone number is " + phoneSMS);

                if (phoneSMS != null && nameSMS != null) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneSMS, null, message, null, null);
                    Log.d("contactSMS", "Message sent to " + nameSMS);
                }
            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Minservice", "Service removed");
    }

    public static boolean containsNumbers(String input) {
        return input.matches(".*\\d+.*");
    }


}
