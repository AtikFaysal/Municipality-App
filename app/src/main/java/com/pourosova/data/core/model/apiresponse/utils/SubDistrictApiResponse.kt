package com.pourosova.data.core.model.apiresponse.utils

data class SubDistrictApiResponse(
    val `data`: List<SubDistrict>?,
    val status: Boolean?
)

data class SubDistrict(
    val bn_name: String?,
    val id: Int?,
    val name: String?
)