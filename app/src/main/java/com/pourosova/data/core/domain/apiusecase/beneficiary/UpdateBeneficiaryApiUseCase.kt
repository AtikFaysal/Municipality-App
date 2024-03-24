package com.pourosova.data.core.domain.apiusecase.beneficiary

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import com.pourosova.data.core.model.apientity.beneficiary.UpdateBeneficiaryApiEntity
import javax.inject.Inject

class UpdateBeneficiaryApiUseCase @Inject constructor(
    private val repository: AppRepository,
) : ApiUseCaseParams<UpdateBeneficiaryApiUseCase.Params, UpdateBeneficiaryApiEntity> {

    data class Params(
        val serial : String,
        val wordNo : String?,
        val village : String?,
        val name : String,
        val nid : String,
        val phone : String,
        val dateOfBirth : String?,
        val occupation : String?,
        val fatherOrHusband : String?,
        val husbandOrWifeNid : String?,
        val fatherNid : String?,
        val motherName : String?
    )

    override suspend fun execute(params: Params) = repository.updateBeneficiary(params)
}