package com.pourosova.data.core.domain.apiusecase.utils

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.utils.MunicipalityApiEntity
import javax.inject.Inject

class FetchMunicipalityApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<FetchMunicipalityApiUseCase.Params, List<MunicipalityApiEntity>> {

    data class Params(
        val subDistrictId: Int,
    )

    override suspend fun execute(params: Params) = repository.fetchMunicipality(params.subDistrictId)
}