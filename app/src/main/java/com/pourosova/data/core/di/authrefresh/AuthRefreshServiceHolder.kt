package com.pourosova.data.core.di.authrefresh

import com.pourosova.data.core.model.apiresponse.auth.RefreshApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthRefreshApiService{

    @POST("auth/refresh")
    fun refreshToken(): Call<RefreshApiResponse>
}

class AuthRefreshServiceHolder{
    private var authRefreshApi: AuthRefreshApiService? = null
    fun getAuthRefreshApi(): AuthRefreshApiService? {
        return authRefreshApi
    }

    fun setAuthRefreshApi(authRefreshApi: AuthRefreshApiService) {
        this.authRefreshApi = authRefreshApi
    }
}
