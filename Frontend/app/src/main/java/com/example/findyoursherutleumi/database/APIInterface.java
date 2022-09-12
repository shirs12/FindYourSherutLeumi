package com.example.findyoursherutleumi.database;

import com.example.findyoursherutleumi.models.Applicant;
import com.example.findyoursherutleumi.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("applicants")
    Call<List<Applicant>> getAllApplicants();

    @GET("applicants/{id}")
    Call<Applicant> getApplicantById(@Path("id") int id);

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
    Call<Applicant> createNewCoordinator(@Field("first_name") String firstName,
                                       @Field("last_name") String lastName,
                                       @Field("phone_number") String phoneNumber,
                                       @Field("email") String email,
                                       @Field("u_password") String password,
                                       @Field("organization_name") String organizationName);




}
