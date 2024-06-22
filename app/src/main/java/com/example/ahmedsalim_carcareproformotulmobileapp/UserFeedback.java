package com.example.ahmedsalim_carcareproformotulmobileapp;

import com.google.firebase.database.Exclude;

public class UserFeedback {
    public String feedName;
    public String feedRating;
    public String feedComment;
    public String mKey;

    public UserFeedback() {
    }

    public UserFeedback(String feedName, String feedRating, String feedComment) {
        this.feedName = feedName;
        this.feedRating = feedRating;
        this.feedComment = feedComment;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedRating() {
        return feedRating;
    }

    public void setFeedRating(String feedRating) {
        this.feedRating = feedRating;
    }

    public String getFeedComment() {
        return feedComment;
    }

    public void setFeedComment(String feedComment) {
        this.feedComment = feedComment;
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
