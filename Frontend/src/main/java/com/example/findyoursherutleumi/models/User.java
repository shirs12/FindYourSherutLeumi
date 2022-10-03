package com.example.findyoursherutleumi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("u_password")
    @Expose
    private String uPassword;
    @SerializedName("level_user_id")
    @Expose
    private Integer userTypeId;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUPassword() {
        return uPassword;
    }

    public void setUPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", uPassword='" + uPassword + '\'' +
                '}';
    }
}


