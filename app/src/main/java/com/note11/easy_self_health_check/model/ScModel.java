package com.note11.easy_self_health_check.model;

public class ScModel {

    private String screenShot;
    private String lastTime;

    public ScModel(){}

    public ScModel(String screenShot, String lastTime) {
        this.screenShot = screenShot;
        this.lastTime = lastTime;
    }

    public String getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(String screenShot) {
        this.screenShot = screenShot;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
