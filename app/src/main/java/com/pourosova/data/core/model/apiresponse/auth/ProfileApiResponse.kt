package com.pourosova.data.core.model.apiresponse.auth

data class ProfileApiResponse(
    val `data`: ProfileData?,
    val status: Boolean?
)

data class ProfileData(
    val email: String?,
    val name: String?,
    val phone: String?,
    val profile_pic: String?
)