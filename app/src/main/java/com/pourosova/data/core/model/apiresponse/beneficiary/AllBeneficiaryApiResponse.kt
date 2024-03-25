package com.pourosova.data.core.model.apiresponse.beneficiary

data class AllBeneficiaryApiResponse(
    val `data`: List<BeneficiaryDetails>?,
    val status: Boolean?
)

data class BeneficiaryDetails(
    val father_or_husband_name: String?,
    val husband_wife_nid: String?,
    val name: String?,
    val nid_no: String?,
    val phone: String?,
    val serial_no: String?,
    val village: String?,
    val word_no: String?,
    val date_of_birth: String?,
    val mother_name: String?,
    val father_nid: String?,
    val pesha: String?,
    val pic: String?,
)