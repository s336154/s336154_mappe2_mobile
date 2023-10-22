package com.example.s336154_mappe2;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import java.util.List;

public class MyService extends Service {

    private MeetingAdapter meetingAdapter;
    private ContactAdapter contactAdapter;
    List<Long> meetingsIDs;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService","Service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        meetingAdapter = new MeetingAdapter(this);
        meetingAdapter.open();

        contactAdapter = new ContactAdapter(this);
        contactAdapter.open();
        
        Toast.makeText(getApplicationContext(), "In MyService", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        SharedPreferences sharedPref = getDefaultSharedPreferences(this);
        boolean checkedSMS = sharedPref.getBoolean("activateSMS", true);
        String Str_checkedSMS = String.valueOf(checkedSMS);
        Log.d("checkedSMS", Str_checkedSMS);

        meetingsIDs = meetingAdapter.getMeetingIdsWithTodayDate(this);

        for (long i : meetingsIDs) {
            Log.d("Matching IDs", "Meeting with ID: " + String.valueOf(i) + " takesplace today.");
        }


        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_MUTABLE);
        Notification notifyAvtale = new NotificationCompat.Builder(this,"MinKanal")
                .setContentTitle("Avtale")
                .setContentText("Du har avtale i dag.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pIntent).build();
        notifyAvtale.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(88, notifyAvtale);
        Log.d("MyService","In MyService");

        if(checkedSMS) {
            sendMessage();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendMessage() {

        String nameSMS = null;
        String phoneSMS = null;
        String messageSMS = null;
        long idSMS = 0;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String phoneNumber = null;

        if (meetingsIDs.size() > 0) {
            for(long id : meetingsIDs) {
                idSMS = meetingAdapter.getContactIdForMeeting(id);
                nameSMS = contactAdapter.getContactName(idSMS);
                phoneSMS = contactAdapter.getContactPhone(idSMS);

                String defaultSMS = "Hei. Dette er en påminelse på avtalen som vi har i dag. Vi ses!";
                String message = sharedPreferences.getString("messageSMS", "");

                Log.d("contactSMS", "Contact name is " + nameSMS);
                Log.d("contactSMS", "Phone number is " + phoneSMS);

                if (!message.isEmpty()) {
                    messageSMS = message;
                }
                else {
                    messageSMS = defaultSMS;
                }
                if(phoneSMS != null && nameSMS != null)   {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneSMS, null, messageSMS, null, null);
                    Toast.makeText(this, "SMS sendt til " +nameSMS, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Minservice", "Service fjernet");
    }






}