package com.pourosova.data.core.data.mapper.beneficiary

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.beneficiary.UpdatePhotoApiEntity
import com.pourosova.data.core.model.apiresponse.beneficiary.UpdatePhotoApiResponse
import javax.inject.Inject

class UpdatePhotoApiMapper @Inject constructor() :
    Mapper<UpdatePhotoApiResponse, UpdatePhotoApiEntity> {
    override fun mapFromApiResponse(type: UpdatePhotoApiResponse): UpdatePhotoApiEntity {
        return UpdatePhotoApiEntity(
            status = type.status ?: false,
            imageUrl = type.data?.image_url ?: "",
        )
    }
}