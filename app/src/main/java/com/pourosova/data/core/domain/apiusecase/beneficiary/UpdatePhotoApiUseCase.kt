package com.pourosova.data.core.domain.apiusecase.beneficiary

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.beneficiary.UpdatePhotoApiEntity
import java.io.File
import javax.inject.Inject

class UpdatePhotoApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<UpdatePhotoApiUseCase.Params, UpdatePhotoApiEntity> {

    data class Params(
        val image: File?,
        val nidOrSerial: String,
    )

    override suspend fun execute(params: Params) = repository.photoUpdate(params)
}