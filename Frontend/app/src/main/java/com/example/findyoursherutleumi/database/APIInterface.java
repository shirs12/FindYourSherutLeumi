package com.example.findyoursherutleumi.database;

import com.example.findyoursherutleumi.models.Applicant;
import com.example.findyoursherutleumi.models.Coordinator;
import com.example.findyoursherutleumi.models.Service;
import com.example.findyoursherutleumi.models.ServiceEdit;
import com.example.findyoursherutleumi.models.ServicePartial;
import com.example.findyoursherutleumi.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This interface has the http requests from the server,
 * that the user needs while using the app.
 */
public interface APIInterface {

    // GET requests
    @GET("services/{id}")
    Call<Service> getServiceById(@Path("id") int id);

    @GET("services")
    Call<List<ServicePartial>> getAllServicesPartially();

    @GET("services/coordinator/{id}")
    Call<List<Service>> getServicesByCoordinatorId(@Path("id") int id);

    @GET("coordinators/{id}")
    Call<Coordinator> getCoordinatorById(@Path("id") int id);

    @GET("coordinators/email/{email}")
    Call<Coordinator> getCoordinatorByEmail(@Path("email") String email);

    @GET("applicants/email/{email}")
    Call<Applicant> getApplicantByEmail(@Path("email") String email);

    @GET("login/{email}")
    Call<User> getUserByEmail(@Path("email") String email);


    // POST requests
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


    // PUT requests
    @PUT("coordinators/{id}")
    @FormUrlEncoded
    Call<Coordinator> updateCoordinatorById(@Path("id") int coordinator_id,
                                            @Field("first_name") String first_name,
                                            @Field("last_name") String last_name,
                                            @Field("phone_number") String phone_number,
                                            @Field("email") String email,
                                            @Field("u_password") String u_password,
                                            @Field("organization_name") String organization_name);

    @PUT("applicants/{id}")
    @FormUrlEncoded
    Call<Applicant> updateApplicantById(@Path("id") int applicant_id,
                                            @Field("first_name") String first_name,
                                            @Field("last_name") String last_name,
                                            @Field("phone_number") String phone_number,
                                            @Field("city") String city,
                                            @Field("email") String email,
                                            @Field("u_password") String u_password);

    @PUT("services/{id}")
    @FormUrlEncoded
    Call<ServiceEdit> updateServiceById(@Path("id") int service_id,
                                        @Field("service_name") String service_name,
                                        @Field("organization_name") String organization_name,
                                        @Field("category") String category,
                                        @Field("country") String country,
                                        @Field("city") String city,
                                        @Field("address") String address,
                                        @Field("has_apartment") int has_apartment,
                                        @Field("is_second_year_only") int is_second_year_only,
                                        @Field("is_morning_service") int is_morning_service,
                                        @Field("is_evening_service") int is_evening_service,
                                        @Field("description_service") String description_service
                                    );

    @PUT("login/forgotpassword/{email}")
    Call<ResponseBody> updateUserPassword(@Path("email") String email);


    // DELETE requests
    @DELETE("services/{id}")
    Call<ResponseBody> deleteServiceById(@Path("id") int service_id);

    @DELETE("applicants/{id}")
    Call<ResponseBody> deleteApplicantById(@Path("id") int applicant_id);

    @DELETE("coordinators/{id}")
    Call<ResponseBody> deleteCoordinatorById(@Path("id") int coordinator_id);

    @DELETE("services/coordinator/delete/{id}")
    Call<ResponseBody> deleteServicesByCoordinatorId (@Path("id") int coordinator_id);


}
