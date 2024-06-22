package com.example.ahmedsalim_carcareproformotulmobileapp;

import com.google.firebase.database.Exclude;

public class UserProduct {
    public String proName;
    public String proPrice;
    public String proDetail;
    public String proQuantity;
    public String proUrl;

    public String mKey;

    public UserProduct() {
    }

    public UserProduct(String proName, String proPrice, String proDetail, String proQuantity, String proUrl) {
        this.proName = proName;
        this.proPrice = proPrice;
        this.proDetail = proDetail;
        this.proQuantity = proQuantity;
        this.proUrl = proUrl;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProDetail() {
        return proDetail;
    }

    public void setProDetail(String proDetail) {
        this.proDetail = proDetail;
    }

    public String getProQuantity() {
        return proQuantity;
    }

    public void setProQuantity(String proQuantity) {
        this.proQuantity = proQuantity;
    }

    public String getProUrl() {
        return proUrl;
    }

    public void setProUrl(String proUrl) {
        this.proUrl = proUrl;
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
