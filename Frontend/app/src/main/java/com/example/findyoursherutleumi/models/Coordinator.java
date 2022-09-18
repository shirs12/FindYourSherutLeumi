//package com.example.findyoursherutleumi.models;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Coordinator {
//
//    @SerializedName("coordinator_id")
//    @Expose
//    private Integer coordinatorId;
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
//    @SerializedName("organization_name")
//    @Expose
//    private String organizationName;
//    @SerializedName("level_user_id")
//    @Expose
//    private Integer levelUserId;
//
//    public Integer getCoordinatorId() {
//        return coordinatorId;
//    }
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
//    public String getUPassword() {
//        return uPassword;
//    }
//
//    public void setUPassword(String uPassword) {
//        this.uPassword = uPassword;
//    }
//
//    public String getOrganizationName() {
//        return organizationName;
//    }
//
//    public void setOrganizationName(String organizationName) {
//        this.organizationName = organizationName;
//    }
//
//    public Integer getLevelUserId() {
//        return levelUserId;
//    }
//
//}
//


package com.example.findyoursherutleumi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinator {

    @SerializedName("coordinator_id")
    @Expose
    private Integer coordinatorId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("u_password")
    @Expose
    private String uPassword;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("level_user_id")
    @Expose
    private Integer levelUserId;

    public Integer getCoordinatorId() {
        return coordinatorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getLevelUserId() {
        return levelUserId;
    }

}

