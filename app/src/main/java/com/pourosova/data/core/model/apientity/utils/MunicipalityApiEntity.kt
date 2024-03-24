package com.pourosova.data.core.model.apientity.utils

data class MunicipalityApiEntity(
    val id: Int,
    val name: String,
    val subDistrictId: Int,
){
    override fun toString(): String {
        return name
    }
}
