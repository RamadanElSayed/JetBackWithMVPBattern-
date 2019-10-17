package com.mobile.mvpwithdatabinding.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Contact implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int mId;

    private String mName;

    private String mMobile;

    private String mEmail;

    private String mImageUrl;

    private String mPassword;

    public Contact() {
    }

    @Ignore
    public Contact(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    @Ignore
    public Contact(String mName, String mMobile, String mEmail, String mImageUrl, String password) {
        this.mName = mName;
        this.mMobile = mMobile;
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
        this.mPassword = password;
    }

    @Ignore
    public Contact(int mId, String mName, String mMobile, String mEmail, String mImageUrl, String password) {
        this.mId = mId;
        this.mName = mName;
        this.mMobile = mMobile;
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
        this.mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
