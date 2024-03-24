package com.pourosova.data.core.data.mapper.utils

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.utils.SubDistrictApiEntity
import com.pourosova.data.core.model.apiresponse.utils.SubDistrictApiResponse
import javax.inject.Inject

class SubDistrictApiMapper @Inject constructor() :
    Mapper<SubDistrictApiResponse, List<SubDistrictApiEntity>> {
    override fun mapFromApiResponse(type: SubDistrictApiResponse): List<SubDistrictApiEntity> {
        return type.data?.map {
            SubDistrictApiEntity(
                id = it.id ?: -1,
                name = it.name ?: "",
                bnName = it.bn_name ?: ""
            )
        } ?: emptyList()
    }
}