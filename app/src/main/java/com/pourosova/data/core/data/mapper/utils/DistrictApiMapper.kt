package com.pourosova.data.core.data.mapper.utils

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.utils.DistrictApiEntity
import com.pourosova.data.core.model.apiresponse.utils.DistrictApiResponse
import javax.inject.Inject

class DistrictApiMapper @Inject constructor() :
    Mapper<DistrictApiResponse, List<DistrictApiEntity>> {
    override fun mapFromApiResponse(type: DistrictApiResponse): List<DistrictApiEntity> {
        return type.data?.map {
            DistrictApiEntity(
                id = it.id ?: -1,
                name = it.name ?: "",
                bnName = it.bn_name ?: ""
            )
        } ?: emptyList()
    }
}