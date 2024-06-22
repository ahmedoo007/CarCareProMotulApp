package com.example.ahmedsalim_carcareproformotulmobileapp;

public class UserInformation {
    public String userName;
    public String userPhone;
    public String userAddress;
    public String userGender;

    public UserInformation() {
    }

    public UserInformation(String userName, String userPhone, String userAddress, String userGender) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
