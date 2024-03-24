package com.pourosova.data.core.model.apientity.beneficiary

data class UpdateBeneficiaryApiEntity(
    val status : Boolean,
    val beneficiaryDetails : BeneficiaryApiEntity
)
