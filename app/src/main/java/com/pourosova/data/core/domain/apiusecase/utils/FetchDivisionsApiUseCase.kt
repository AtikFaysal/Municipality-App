package com.pourosova.data.core.domain.apiusecase.utils

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseNonParams
import com.pourosova.data.core.model.apientity.utils.DivisionApiEntity
import javax.inject.Inject

class FetchDivisionsApiUseCase @Inject constructor(
    private val repository: AppRepository
): ApiUseCaseNonParams<List<DivisionApiEntity>> {
    override suspend fun execute() = repository.fetchDivisions()
}