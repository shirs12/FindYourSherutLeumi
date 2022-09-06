package com.example.findyoursherutleumi.database;

import com.example.findyoursherutleumi.models.Applicant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("applicants")
    Call<List<Applicant>> getAllApplicants();

    @GET("applicants/{id}")
    Call<Applicant> getApplicantById(@Path("id") int id);

//    @POST("/signin")



}
