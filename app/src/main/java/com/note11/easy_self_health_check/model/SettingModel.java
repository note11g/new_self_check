package com.note11.easy_self_health_check.model;

public class SettingModel {

    private boolean finger;
    private int time;

    public SettingModel(){}

    public SettingModel(boolean finger, int time) {
        this.finger = finger;
        this.time = time;
    }

    public boolean isFinger() {
        return finger;
    }

    public void setFinger(boolean finger) {
        this.finger = finger;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
