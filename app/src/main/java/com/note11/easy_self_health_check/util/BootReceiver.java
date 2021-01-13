package com.note11.easy_self_health_check.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            final int t = SettingCache.getSetting(context).getTime();
            MakeAlarm.makeAlarm(t / 100, t % 100, context);

        }
    }
}
