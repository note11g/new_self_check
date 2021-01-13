package com.note11.easy_self_health_check.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.note11.easy_self_health_check.model.ScModel;
import com.note11.easy_self_health_check.model.ScModel;

public class ScCache {

    public static SharedPreferences getShared(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setSc(Context context, ScModel model){
        Gson gson = new Gson();
        String json = gson.toJson(model);
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("sc_photo",json).apply();
    }

    public static ScModel getSc(Context context){
        Gson gson = new Gson();
        return gson.fromJson(getShared(context).getString("sc_photo",""), ScModel.class);
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = getShared(context).edit();
        editor.putString("sc_photo", null).apply();
    }

}
