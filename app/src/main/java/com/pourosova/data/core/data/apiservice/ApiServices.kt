package com.pourosova.data.core.data.apiservice

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    @POST("auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<String>

    @POST("auth/me")
    fun fetchProfile(): Call<String>

    @POST("auth/refresh")
    fun refreshToken(): Call<String>

    @POST("auth/logout")
    fun logout(): Call<String>

    @GET("divisions")
    fun fetchDivisions(): Call<String>

    @GET("get-districts-by-division")
    fun fetchDistricts(
        @Query("division_id") divisionId : Int
    ): Call<String>

    @GET("get-upazilas-by-district")
    fun fetchSubDistricts(
        @Query("district_id") districtId : Int
    ): Call<String>

    @GET("get-up_muni-by-upazila")
    fun fetchPouroshova(
        @Query("upazila_id") subDistrictId : Int
    ): Call<String>

    @GET("get-up_muni-by-upazila")
    fun fetchUserList(
        @Query("up_muni_id") id : Int
    ): Call<String>

    @GET("beneficiary-profile")
    fun fetchSingleUserProfile(
        @Query("up_muni_id") id : Int,
        @Query("nid_or_serial") nid : String,
    ): Call<String>

    @POST("beneficiary-image-upload")
    @FormUrlEncoded
    fun updateUserImage(
        @Field("serial_no") serialNo : String,
        @Field("image") image : String,
    ): Call<String>

    @POST("eneficiary-data-update")
    @FormUrlEncoded
    fun updateUserData(
        @Field("serial_no") serialNo : String,
        @Field("word_no") wordNo : String,
        @Field("village") village : String,
        @Field("name") name : String,
        @Field("nid_no") nidNo : String,
        @Field("phone") phone : String,
        @Field("date_of_birth") dateOfBirth : String,
        @Field("pesha") pesha : String,
        @Field("father_or_husband_name") fatherOrHusbandName : String,
        @Field("husband_wife_nid") husbandWifeNid : String,
        @Field("father_nid") fatherNid : String,
        @Field("mother_name") motherName : String,
    ): Call<String>
}

class AuthRefreshServiceHolder{
    private var authRefreshApi: ApiServices? = null
    fun getAuthRefreshApi(): ApiServices? {
        return authRefreshApi
    }

    fun setAuthRefreshApi(authRefreshApi: ApiServices) {
        this.authRefreshApi = authRefreshApi
    }
}