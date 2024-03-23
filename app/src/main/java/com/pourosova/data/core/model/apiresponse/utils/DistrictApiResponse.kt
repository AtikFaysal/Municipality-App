package com.pourosova.data.core.model.apiresponse.utils

data class DistrictApiResponse(
    val `data`: List<District>?,
    val status: Boolean?
)

data class District(
    val bn_name: String?,
    val id: Int?,
    val name: String?
)