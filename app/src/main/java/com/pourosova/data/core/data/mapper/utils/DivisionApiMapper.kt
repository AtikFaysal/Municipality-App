package com.pourosova.data.core.data.mapper.utils

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.utils.DivisionApiEntity
import com.pourosova.data.core.model.apiresponse.utils.DivisionsApiResponse
import javax.inject.Inject

class DivisionApiMapper @Inject constructor() :
    Mapper<DivisionsApiResponse, List<DivisionApiEntity>> {
    override fun mapFromApiResponse(type: DivisionsApiResponse): List<DivisionApiEntity> {
        return type.data?.map {
            DivisionApiEntity(
                id = it.id ?: -1,
                name = it.name ?: "",
                bnName = it.bn_name ?: ""
            )
        } ?: emptyList()
    }
}