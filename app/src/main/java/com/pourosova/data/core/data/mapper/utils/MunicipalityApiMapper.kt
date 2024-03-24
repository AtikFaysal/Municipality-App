package com.pourosova.data.core.data.mapper.utils

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.utils.MunicipalityApiEntity
import com.pourosova.data.core.model.apiresponse.utils.MunicipalityApiResponse
import javax.inject.Inject

class MunicipalityApiMapper @Inject constructor() :
    Mapper<MunicipalityApiResponse, List<MunicipalityApiEntity>> {
    override fun mapFromApiResponse(type: MunicipalityApiResponse): List<MunicipalityApiEntity> {
        return type.data?.map {
            MunicipalityApiEntity(
                id = it.id ?: -1,
                name = it.name ?: "",
                subDistrictId = it.up_muni?.upazila_id ?: -1
            )
        } ?: emptyList()
    }
}