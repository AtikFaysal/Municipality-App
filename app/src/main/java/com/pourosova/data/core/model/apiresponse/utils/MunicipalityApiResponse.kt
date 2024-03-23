package com.pourosova.data.core.model.apiresponse.utils

data class MunicipalityApiResponse(
    val `data`: List<Municipality>?,
    val status: Boolean?
)

data class Municipality(
    val id: Int?,
    val name: String?,
    val up_muni: UpMuni?
)

data class UpMuni(
    val upazila_id: Int?,
    val user_id: Int?
)