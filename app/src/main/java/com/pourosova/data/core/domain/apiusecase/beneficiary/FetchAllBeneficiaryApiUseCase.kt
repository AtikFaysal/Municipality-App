package com.pourosova.data.core.domain.apiusecase.beneficiary

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import javax.inject.Inject

class FetchAllBeneficiaryApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<FetchAllBeneficiaryApiUseCase.Params, List<BeneficiaryApiEntity>> {

    data class Params(
        val municipalityId : Int,
    )

    override suspend fun execute(params: Params) = repository.fetchAllBeneficiary(params.municipalityId)
}