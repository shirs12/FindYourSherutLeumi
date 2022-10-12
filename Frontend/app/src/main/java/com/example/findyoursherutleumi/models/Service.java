package com.example.findyoursherutleumi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is service model
 */
public class Service {

    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("has_apartment")
    @Expose
    private Integer hasApartment;
    @SerializedName("is_second_year_only")
    @Expose
    private Integer isSecondYearOnly;
    @SerializedName("is_morning_service")
    @Expose
    private Integer isMorningService;
    @SerializedName("is_evening_service")
    @Expose
    private Integer isEveningService;
    @SerializedName("description_service")
    @Expose
    private String descriptionService;
    @SerializedName("coordinator_name")
    @Expose
    private String coordinatorName;
    @SerializedName("coordinator_id")
    @Expose
    private Integer coordinatorId;

    public Integer getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getHasApartment() {
        if (hasApartment == 0)
            return false;
        return true;
    }

    public void setHasApartment(Integer hasApartment) {
        this.hasApartment = hasApartment;
    }

    public boolean getIsSecondYearOnly() {
        if (isSecondYearOnly == 0)
            return false;
        return true;

    }

    public void setIsSecondYearOnly(Integer isSecondYearOnly) {
        this.isSecondYearOnly = isSecondYearOnly;

    }

    public boolean getIsMorningService() {
        if (isMorningService == 0)
            return false;
        return true;
    }

    public void setIsMorningService(Integer isMorningService) {
        this.isMorningService = isMorningService;
    }

    public boolean getIsEveningService() {
        if (isEveningService == 0)
            return false;
        return true;
    }

    public void setIsEveningService(Integer isEveningService) {
        this.isEveningService = isEveningService;
    }

    public String getDescriptionService() {
        return descriptionService;
    }

    public void setDescriptionService(String descriptionService) {
        this.descriptionService = descriptionService;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public Integer getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(Integer coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", category='" + category + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", hasApartment=" + hasApartment +
                ", isSecondYearOnly=" + isSecondYearOnly +
                ", isMorningService=" + isMorningService +
                ", isEveningService=" + isEveningService +
                ", descriptionService='" + descriptionService + '\'' +
                ", coordinatorName='" + coordinatorName + '\'' +
                ", coordinatorId=" + coordinatorId +
                '}';
    }
}

