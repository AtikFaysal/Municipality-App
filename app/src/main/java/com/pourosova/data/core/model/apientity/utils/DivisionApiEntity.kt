package com.pourosova.data.core.model.apientity.utils

data class DivisionApiEntity(
    val bnName: String,
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}
