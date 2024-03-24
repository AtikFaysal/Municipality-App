package com.pourosova.data.core.data.mapper.beneficiary

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import com.pourosova.data.core.model.apiresponse.beneficiary.BeneficiaryApiResponse
import javax.inject.Inject

class BeneficiaryApiMapper @Inject constructor() : Mapper<BeneficiaryApiResponse, BeneficiaryApiEntity> {
    override fun mapFromApiResponse(type: BeneficiaryApiResponse): BeneficiaryApiEntity {
        return BeneficiaryApiEntity(
            fatherOrHusbandName = type.data?.father_or_husband_name ?: "",
            husbandWifeNid = type.data?.husband_wife_nid ?: "",
            name = type.data?.name ?: "",
            nidNo = type.data?.nid_no ?: "",
            phone = type.data?.phone ?: "",
            serialNo = type.data?.serial_no ?: "",
            village = type.data?.village ?: "",
            wordNo = type.data?.word_no ?: "",
            motherName = type.data?.word_no ?: "",
            fatherNid = type.data?.word_no ?: "",
            occupation = type.data?.word_no ?: "",
            dob = type.data?.word_no ?: "",
        )
    }
}