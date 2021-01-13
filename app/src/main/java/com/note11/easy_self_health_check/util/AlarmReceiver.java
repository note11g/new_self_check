package com.note11.easy_self_health_check.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.note11.easy_self_health_check.R;
import com.note11.easy_self_health_check.Screen.MainScreen.SplashActivity;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public final static int NOTIFICATION_ID = 100;
    public final String PRIMARY_CHANNEL_ID = "fast_self_check_notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar cal = Calendar.getInstance();
        if(Calendar.SUNDAY!=cal.get(Calendar.DAY_OF_WEEK)
        && Calendar.SATURDAY !=cal.get(Calendar.DAY_OF_WEEK)) {
            if (cal.get(Calendar.AM_PM) == Calendar.AM) {//TODO change for more better way...

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Intent notificationIntent = new Intent(context, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingI = PendingIntent.getActivity(context,
                        NOTIFICATION_ID,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    String channelName = "빠른 자가진단 알림";
                    String description = "빠른 자가진단을 위한 알림입니다.";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel channel = new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            channelName,
                            importance);
                    channel.setDescription(description);
                    channel.enableLights(true);
                    channel.enableVibration(true);

                    if (notificationManager != null)
                        notificationManager.createNotificationChannel(channel);
                }

                builder.setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentTitle("자가진단을 진행해주세요.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("빠른 자가진단과 함께 오늘도 힘찬 하루 보내세요!")
                        .setContentIntent(pendingI);

                if (notificationManager != null) {
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        }
    }
}
