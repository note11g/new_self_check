package com.note11.easy_self_health_check.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.note11.easy_self_health_check.model.SettingModel;

public class SettingCache {

    public static SharedPreferences getShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setSetting(Context context, SettingModel model){
        Gson gson = new Gson();
        String json = gson.toJson(model);
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("set_fast",json).apply();
    }

    public static SettingModel getSetting(Context context){
        Gson gson = new Gson();
        return gson.fromJson(getShared(context).getString("set_fast",""), SettingModel.class);
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("set_fast", null).apply();
    }

}