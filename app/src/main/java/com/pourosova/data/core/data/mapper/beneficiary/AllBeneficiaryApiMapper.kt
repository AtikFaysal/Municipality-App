package com.pourosova.data.core.data.mapper.beneficiary

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import com.pourosova.data.core.model.apiresponse.beneficiary.AllBeneficiaryApiResponse
import javax.inject.Inject

class AllBeneficiaryApiMapper @Inject constructor() :
    Mapper<AllBeneficiaryApiResponse, List<BeneficiaryApiEntity>> {
    override fun mapFromApiResponse(type: AllBeneficiaryApiResponse): List<BeneficiaryApiEntity> {
        return type.data?.map {
            BeneficiaryApiEntity(
                fatherOrHusbandName = it.father_or_husband_name ?: "",
                husbandWifeNid = it.husband_wife_nid ?: "",
                name = it.name ?: "",
                nidNo = it.nid_no ?: "",
                phone = it.phone ?: "",
                serialNo = it.serial_no ?: "",
                village = it.village ?: "",
                wordNo = it.word_no ?: "",
                motherName = it.mother_name ?: "",
                fatherNid = it.father_nid ?: "",
                occupation = it.pesha ?: "",
                dob = it.date_of_birth ?: "",
            )
        } ?: emptyList()
    }
}