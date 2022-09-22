package com.example.findyoursherutleumi.database;

import com.example.findyoursherutleumi.models.Applicant;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;
import com.example.findyoursherutleumi.models.ServicePartial;
import com.example.findyoursherutleumi.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("applicants")
    Call<List<Applicant>> getAllApplicants();

    @GET("applicants/{id}")
    Call<Applicant> getApplicantById(@Path("id") int id);

    @GET("services/{id}")
    Call<Service> getServiceById(@Path("id") int id);

    @GET("services")
    Call<List<ServicePartial>> getAllServicesPartially();

    @GET("coordinators/{id}")
    Call<Coordinator> getCoordinatorById(@Path("id") int id);

    @GET("coordinators/email/{email}")
    Call<Coordinator> getCoordinatorByEmail(@Path("email") String email);


    @POST("login")
    @FormUrlEncoded
    Call<User> authenticateUser(@Field("email") String email,
                                @Field("u_password") String password);

    @POST("applicants/add")
    @FormUrlEncoded
    Call<Applicant> createNewApplicant(@Field("first_name") String firstName,
                                       @Field("last_name") String lastName,
                                       @Field("phone_number") String phoneNumber,
                                       @Field("city") String city,
                                       @Field("email") String email,
                                       @Field("u_password") String password);

    @POST("coordinators/add")
    @FormUrlEncoded
    Call<Coordinator> createNewCoordinator(@Field("first_name") String firstName,
                                           @Field("last_name") String lastName,
                                           @Field("phone_number") String phoneNumber,
                                           @Field("email") String email,
                                           @Field("u_password") String password,
                                           @Field("organization_name") String organizationName);

    @POST("services/add")
    @FormUrlEncoded
    Call<Service> createNewService(@Field("service_name") String serviceName,
                                           @Field("organization_name") String organizationName,
                                           @Field("category") String category,
                                           @Field("country") String country,
                                           @Field("city") String city,
                                           @Field("address") String address,
                                           @Field("has_apartment") Integer hasApartment,
                                           @Field("is_second_year_only") Integer secondYear,
                                           @Field("is_morning_service") Integer isMorning,
                                           @Field("is_evening_service") Integer isEvening,
                                           @Field("description_service") String description,
                                           @Field("coordinator_name") String coordinatorName,
                                           @Field("coordinator_id") int coordinatorId);


    @PUT("coordinators/{id}")
    @FormUrlEncoded
    Call<Coordinator> updateCoordinatorById(@Field("coordinator_id") int id,
                                            @Field("first_name") String first_name,
                                            @Field("last_name") String last_name,
                                            @Field("phone_number") String phone_number,
                                            @Field("email") String email,
                                            @Field("u_password") String u_password,
                                            @Field("organization_name") String organization_name);


}
