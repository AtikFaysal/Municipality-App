package com.pourosova.data.core.model.apiresponse.auth

data class RefreshApiResponse(
    val access_token: String?,
    val expires_in: Int?,
    val token_type: String?,
    val status: Boolean?,
    val message: String?,
)