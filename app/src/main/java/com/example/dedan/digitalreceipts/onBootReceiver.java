package com.example.dedan.digitalreceipts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class onBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Intent yesterdayintent=new Intent(context,MyReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,yesterdayintent,0);
            AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            calendar.set(Calendar.MINUTE, 10);
            long time=calendar.getTimeInMillis();
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,time,AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

            //week
            Intent weekIntent=new Intent(context,weekReceiver.class);
            PendingIntent weekpendingIntent=PendingIntent.getBroadcast(context,0,weekIntent,0);
            AlarmManager weekalarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);

            Calendar weekcalendar = Calendar.getInstance();
            weekcalendar.setTimeInMillis(System.currentTimeMillis());
            weekcalendar.set(Calendar.DAY_OF_WEEK, 3);
            weekcalendar.set(Calendar.HOUR_OF_DAY, 18);
            long timeWeek=weekcalendar.getTimeInMillis();
            weekalarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeWeek,AlarmManager.INTERVAL_DAY*7, weekpendingIntent);

            //month
            Intent monthIntent=new Intent(context,monthReceiver.class);
            PendingIntent monthpendingIntent=PendingIntent.getBroadcast(context,0,monthIntent,0);
            AlarmManager monthalarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);

            Calendar monthcalendar = Calendar.getInstance();
            monthcalendar.setTimeInMillis(System.currentTimeMillis());
            monthcalendar.set(Calendar.WEEK_OF_MONTH, 1);
            monthcalendar.set(Calendar.DAY_OF_WEEK, 1);
            monthcalendar.set(Calendar.HOUR_OF_DAY,0);
            long timeMonth=monthcalendar.getTimeInMillis();
            monthalarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeMonth,AlarmManager.INTERVAL_DAY, monthpendingIntent);
        }
    }
}
