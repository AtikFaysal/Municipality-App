package com.pourosova.data.core.domain

import com.pourosova.data.core.domain.apiusecase.auth.LoginApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdateBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdatePhotoApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.model.apientity.auth.LoginApiEntity
import com.pourosova.data.core.model.apientity.auth.LogoutApiEntity
import com.pourosova.data.core.model.apientity.auth.ProfileApiEntity
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import com.pourosova.data.core.model.apientity.beneficiary.UpdateBeneficiaryApiEntity
import com.pourosova.data.core.model.apientity.beneficiary.UpdatePhotoApiEntity
import com.pourosova.data.core.model.apientity.utils.DistrictApiEntity
import com.pourosova.data.core.model.apientity.utils.DivisionApiEntity
import com.pourosova.data.core.model.apientity.utils.MunicipalityApiEntity
import com.pourosova.data.core.model.apientity.utils.SubDistrictApiEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun login(params : LoginApiUseCase.Params) : Flow<ApiResult<LoginApiEntity>>

    suspend fun fetchProfile() : Flow<ApiResult<ProfileApiEntity>>

    suspend fun logout() : Flow<ApiResult<LogoutApiEntity>>

    suspend fun fetchDivisions() : Flow<ApiResult<List<DivisionApiEntity>>>

    suspend fun fetchDistrict(divisionId : Int) : Flow<ApiResult<List<DistrictApiEntity>>>

    suspend fun fetchSubDistrict(districtId : Int) : Flow<ApiResult<List<SubDistrictApiEntity>>>

    suspend fun fetchMunicipality(subDistrictId : Int) : Flow<ApiResult<List<MunicipalityApiEntity>>>

    suspend fun fetchAllBeneficiary(muniId : Int) : Flow<ApiResult<List<BeneficiaryApiEntity>>>

    suspend fun fetchBeneficiary(params : FetchBeneficiaryApiUseCase.Params) : Flow<ApiResult<BeneficiaryApiEntity>>

    suspend fun photoUpdate(params : UpdatePhotoApiUseCase.Params) : Flow<ApiResult<UpdatePhotoApiEntity>>

    suspend fun updateBeneficiary(params : UpdateBeneficiaryApiUseCase.Params) : Flow<ApiResult<UpdateBeneficiaryApiEntity>>
}