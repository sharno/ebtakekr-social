package com.zft.pojo;

/**
 * Created by Rainfall on 4/17/2015.
 */
public class User {
    private String hashedPhone;
    private String userSignature;
    private String fbToken;

    public User(String hashedPhone, String userSignature) {
        this.hashedPhone = hashedPhone;
        this.userSignature = userSignature;
    }

    public User(String hashedPhone, String userSignature, String fbToken) {
        this.hashedPhone = hashedPhone;
        this.userSignature = userSignature;
        this.fbToken = fbToken;
    }

    public String getHashedPhone() {
        return hashedPhone;
    }

    public void setHashedPhone(String hashedPhone) {
        this.hashedPhone = hashedPhone;
    }

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }
}
