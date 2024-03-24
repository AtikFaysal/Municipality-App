package com.pourosova.data.core.model.apientity.auth

data class LoginApiEntity(
    val accessToken: String,
    val expiresAt: Int,
    val tokenType: String,
    val status: Boolean,
    val message: String,
)