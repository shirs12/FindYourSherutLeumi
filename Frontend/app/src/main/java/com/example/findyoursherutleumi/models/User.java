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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", uPassword='" + uPassword + '\'' +
                '}';
    }
}


//package com.example.findyoursherutleumi.models;
//
//import javax.annotation.Generated;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//@Generated("jsonschema2pojo")
//public class User {
//
//    @SerializedName("first_name")
//    @Expose
//    private String firstName;
//    @SerializedName("last_name")
//    @Expose
//    private String lastName;
//    @SerializedName("phone_number")
//    @Expose
//    private String phoneNumber;
//    @SerializedName("email")
//    @Expose
//    private String email;
//    @SerializedName("u_password")
//    @Expose
//    private String uPassword;
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getuPassword() {
//        return uPassword;
//    }
//
//    public void setuPassword(String uPassword) {
//        this.uPassword = uPassword;
//    }
//
//}