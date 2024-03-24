package com.pourosova.data.core.domain.apiusecase.utils

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.utils.SubDistrictApiEntity
import javax.inject.Inject

class FetchSubDistrictApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<FetchSubDistrictApiUseCase.Params, List<SubDistrictApiEntity>> {

    data class Params(
        val districtId: Int,
    )

    override suspend fun execute(params: Params) = repository.fetchSubDistrict(params.districtId)
}