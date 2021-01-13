package com.note11.easy_self_health_check.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class MakeAlarm {

    public static int makeAlarm(int h, int m, Context c){

        AlarmManager am = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, AlarmReceiver.class);
        PendingIntent ai = PendingIntent.getBroadcast(
                c, AlarmReceiver.NOTIFICATION_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, h);//TODO HOUR OF DAY is OK?
        cal.set(Calendar.MINUTE, m);
        cal.set(Calendar.SECOND, 0);

        am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                ai
        );

        return h * 100 + m;
    }
}
