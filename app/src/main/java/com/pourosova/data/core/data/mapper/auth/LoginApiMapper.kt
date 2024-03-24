package com.pourosova.data.core.data.mapper.auth

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.auth.LoginApiEntity
import com.pourosova.data.core.model.apiresponse.auth.LoginApiResponse
import javax.inject.Inject

class LoginApiMapper @Inject constructor() : Mapper<LoginApiResponse, LoginApiEntity> {
    override fun mapFromApiResponse(type: LoginApiResponse): LoginApiEntity {
        return LoginApiEntity(
            accessToken = type.access_token ?: "",
            expiresAt = type.expires_in ?: 0,
            message = type.message ?: "",
            status = type.status ?: false,
            tokenType = type.token_type ?: ""
        )
    }
}