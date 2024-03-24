package com.pourosova.data.core.data.apiservice

import com.pourosova.data.core.model.apientity.beneficiary.UpdatePhotoApiEntity
import com.pourosova.data.core.model.apiresponse.auth.LoginApiResponse
import com.pourosova.data.core.model.apiresponse.auth.LogoutApiResponse
import com.pourosova.data.core.model.apiresponse.auth.ProfileApiResponse
import com.pourosova.data.core.model.apiresponse.auth.RefreshApiResponse
import com.pourosova.data.core.model.apiresponse.beneficiary.AllBeneficiaryApiResponse
import com.pourosova.data.core.model.apiresponse.beneficiary.BeneficiaryApiResponse
import com.pourosova.data.core.model.apiresponse.beneficiary.UpdatePhotoApiResponse
import com.pourosova.data.core.model.apiresponse.beneficiary.UpdateUserApiResponse
import com.pourosova.data.core.model.apiresponse.utils.DistrictApiResponse
import com.pourosova.data.core.model.apiresponse.utils.DivisionsApiResponse
import com.pourosova.data.core.model.apiresponse.utils.MunicipalityApiResponse
import com.pourosova.data.core.model.apiresponse.utils.SubDistrictApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServices {
    @POST("auth/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginApiResponse>

    @POST("auth/me")
    suspend fun fetchProfile(): Response<ProfileApiResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<LogoutApiResponse>

    @GET("divisions")
    suspend fun fetchDivisions(): Response<DivisionsApiResponse>

    @GET("get-districts-by-division")
    suspend fun fetchDistricts(
        @Query("division_id") divisionId : Int
    ): Response<DistrictApiResponse>

    @GET("get-upazilas-by-district")
    suspend fun fetchSubDistricts(
        @Query("district_id") districtId : Int
    ): Response<SubDistrictApiResponse>

    @GET("get-up_muni-by-upazila")
    suspend fun fetchMunicipality(
        @Query("upazila_id") subDistrictId : Int
    ): Response<MunicipalityApiResponse>

    @GET("get-all-beneficiary")
    suspend fun fetchUserList(
        @Query("up_muni_id") id : Int
    ): Response<AllBeneficiaryApiResponse>

    @GET("beneficiary-profile")
    suspend fun fetchSingleUserProfile(
        @Query("up_muni_id") id : Int,
        @Query("nid_or_serial") nid : String,
    ): Response<BeneficiaryApiResponse>

    @POST("beneficiary-image-upload")
    @Multipart
    suspend fun updateUserImage(
        @Part("serial_no") serialNo : RequestBody,
        @Part image : MultipartBody.Part?,
    ): Response<UpdatePhotoApiResponse>

    @POST("beneficiary-data-update")
    @FormUrlEncoded
    suspend fun updateUserData(
        @Field("serial_no") serialNo : String,
        @Field("word_no") wordNo : String?,
        @Field("village") village : String?,
        @Field("name") name : String,
        @Field("nid_no") nidNo : String,
        @Field("phone") phone : String,
        @Field("date_of_birth") dateOfBirth : String?,
        @Field("pesha") pesha : String?,
        @Field("father_or_husband_name") fatherOrHusbandName : String?,
        @Field("husband_wife_nid") husbandWifeNid : String?,
        @Field("father_nid") fatherNid : String?,
        @Field("mother_name") motherName : String?,
    ): Response<UpdateUserApiResponse>
}
