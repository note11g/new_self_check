package com.note11.easy_self_health_check.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.note11.easy_self_health_check.model.PwModel;

public class PwCache {

    public static SharedPreferences getShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setPw(Context context, PwModel model){
        Gson gson = new Gson();
        String json = gson.toJson(model);
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("pw_fast",json).apply();
    }

    public static PwModel getPw(Context context){
        Gson gson = new Gson();
        return gson.fromJson(getShared(context).getString("pw_fast",""), PwModel.class);
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("pw_fast", null).apply();
    }

}