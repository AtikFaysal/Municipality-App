package com.pourosova.data.core.domain.apiusecase.beneficiary

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import javax.inject.Inject

class FetchBeneficiaryApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<FetchBeneficiaryApiUseCase.Params, BeneficiaryApiEntity> {

    data class Params(
        val municipalityId : Int,
        val nidOrSerial : String,
    )

    override suspend fun execute(params: Params) = repository.fetchBeneficiary(params)
}