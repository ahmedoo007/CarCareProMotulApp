package com.example.ahmedsalim_carcareproformotulmobileapp;

import com.google.firebase.database.Exclude;

public class UserAppointment {
    public String serName;
    public String serTime;
    public String serDate;
    public String serUsername;
    public String serPhone;

    public String mKey;

    public UserAppointment() {
    }

    public UserAppointment(String serName, String serTime, String serDate, String serUsername, String serPhone) {
        this.serName = serName;
        this.serTime = serTime;
        this.serDate = serDate;
        this.serUsername = serUsername;
        this.serPhone = serPhone;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public String getSerTime() {
        return serTime;
    }

    public void setSerTime(String serTime) {
        this.serTime = serTime;
    }

    public String getSerDate() {
        return serDate;
    }

    public void setSerDate(String serDate) {
        this.serDate = serDate;
    }

    public String getSerUsername() {
        return serUsername;
    }

    public void setSerUsername(String serUsername) {
        this.serUsername = serUsername;
    }

    public String getSerPhone() {
        return serPhone;
    }

    public void setSerPhone(String serPhone) {
        this.serPhone = serPhone;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
