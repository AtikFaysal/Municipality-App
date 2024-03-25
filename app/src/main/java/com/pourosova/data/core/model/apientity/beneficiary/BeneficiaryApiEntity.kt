package com.pourosova.data.core.model.apientity.beneficiary

import java.io.Serializable

data class BeneficiaryApiEntity(
    val fatherOrHusbandName: String,
    val husbandWifeNid: String,
    val name: String,
    val nidNo: String,
    val phone: String,
    val serialNo: String,
    val village: String,
    val wordNo: String,
    val dob: String,
    val motherName: String,
    val fatherNid: String,
    val occupation: String,
    val pic: String,
) : Serializable
