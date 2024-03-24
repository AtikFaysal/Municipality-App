package com.pourosova.data.core.domain.apiusecase.utils

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.domain.base.IoValidation
import com.pourosova.data.core.model.apientity.utils.DistrictApiEntity
import javax.inject.Inject

class FetchDistrictApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<FetchDistrictApiUseCase.Params, List<DistrictApiEntity>> {

    data class Params(
        val divisionId: Int,
    )

    override suspend fun execute(params: Params) = repository.fetchDistrict(params.divisionId)
}