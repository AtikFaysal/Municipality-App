package com.pourosova.data.core.di.authrefresh
import com.pourosova.data.sharedpref.SharedPrefHelper
import com.pourosova.data.sharedpref.SpKey
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthenticationRefreshToken @Inject constructor(
    private var sharedPrefHelper: SharedPrefHelper,
    private var authRefreshServiceHolder: AuthRefreshServiceHolder
) : Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == 401){
            val authenticatorCall = authRefreshServiceHolder.getAuthRefreshApi()?.refreshToken()
            val refreshTokenResponse = authenticatorCall?.execute()
            if (refreshTokenResponse?.body() != null && refreshTokenResponse.isSuccessful && refreshTokenResponse.code() == 200){
                refreshTokenResponse.body()?.let {
                    sharedPrefHelper.putString(SpKey.authToken,it.access_token ?: "")
                    response.request.newBuilder().header("Authorization", it.access_token ?: "").build()
                }

            } else null
        } else null
    }

}