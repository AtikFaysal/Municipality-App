package com.pourosova.data.core.model.apiresponse.utils

data class DivisionsApiResponse(
    val `data`: List<Division>?,
    val status: Boolean?
)

data class Division(
    val bn_name: String?,
    val id: Int?,
    val name: String?
)