package com.pourosova.data.core.data.mapper.auth

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.auth.LogoutApiEntity
import com.pourosova.data.core.model.apiresponse.auth.LogoutApiResponse
import javax.inject.Inject

class LogoutApiMapper @Inject constructor() : Mapper<LogoutApiResponse, LogoutApiEntity> {
    override fun mapFromApiResponse(type: LogoutApiResponse): LogoutApiEntity {
        return LogoutApiEntity(
            message = type.message ?: "",
            status = type.status ?: false
        )
    }
}