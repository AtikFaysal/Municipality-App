package com.pourosova.data.core.data.repoimpl

import com.pourosova.data.core.data.NetworkBoundResource
import com.pourosova.data.core.data.apiservice.ApiServices
import com.pourosova.data.core.data.mapper.auth.CacheProfile
import com.pourosova.data.core.data.mapper.auth.LoginApiMapper
import com.pourosova.data.core.data.mapper.auth.LogoutApiMapper
import com.pourosova.data.core.data.mapper.auth.ProfileApiMapper
import com.pourosova.data.core.data.mapper.beneficiary.AllBeneficiaryApiMapper
import com.pourosova.data.core.data.mapper.beneficiary.BeneficiaryApiMapper
import com.pourosova.data.core.data.mapper.beneficiary.UpdateBeneficiaryApiMapper
import com.pourosova.data.core.data.mapper.beneficiary.UpdatePhotoApiMapper
import com.pourosova.data.core.data.mapper.mapFromApiResponse
import com.pourosova.data.core.data.mapper.utils.DistrictApiMapper
import com.pourosova.data.core.data.mapper.utils.DivisionApiMapper
import com.pourosova.data.core.data.mapper.utils.MunicipalityApiMapper
import com.pourosova.data.core.data.mapper.utils.SubDistrictApiMapper
import com.pourosova.data.core.data.util.Utils
import com.pourosova.data.core.domain.AppRepository
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
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class AppRepoImpl @Inject constructor(
    private val networkBoundResources: NetworkBoundResource,
    private val apiService: ApiServices,
    private val loginApiMapper : LoginApiMapper,
    private val logoutApiMapper : LogoutApiMapper,
    private val profileApiMapper : ProfileApiMapper,
    private val cacheProfile : CacheProfile,
    private val divisionApiMapper : DivisionApiMapper,
    private val districtApiMapper : DistrictApiMapper,
    private val subDistrictApiMapper : SubDistrictApiMapper,
    private val municipalityApiMapper : MunicipalityApiMapper,
    private val allBeneficiaryApiMapper : AllBeneficiaryApiMapper,
    private val beneficiaryApiMapper : BeneficiaryApiMapper,
    private val updatePhotoApiMapper : UpdatePhotoApiMapper,
    private val updateBeneficiaryApiMapper : UpdateBeneficiaryApiMapper,
) : AppRepository {

    override suspend fun login(params: LoginApiUseCase.Params): Flow<ApiResult<LoginApiEntity>> {

        Timber.e("params: ${params.email}, ${params.password}")
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.login(
                    email = params.email,
                    password = params.password,
                )
            },
            mapper = loginApiMapper
        )
    }

    override suspend fun fetchProfile(): Flow<ApiResult<ProfileApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchProfile()
            },
            mapper = profileApiMapper
        ).map {
            if (it is ApiResult.Success) {
                cacheProfile.cacheProfile(it.data)
            }
            it
        }
    }

    override suspend fun logout(): Flow<ApiResult<LogoutApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.logout()
            },
            mapper = logoutApiMapper
        )
    }

    override suspend fun fetchDivisions(): Flow<ApiResult<List<DivisionApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchDivisions()
            },
            mapper = divisionApiMapper
        )
    }

    override suspend fun fetchDistrict(divisionId: Int): Flow<ApiResult<List<DistrictApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchDistricts(divisionId)
            },
            mapper = districtApiMapper
        )
    }

    override suspend fun fetchSubDistrict(districtId: Int): Flow<ApiResult<List<SubDistrictApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchSubDistricts(districtId)
            },
            mapper = subDistrictApiMapper
        )
    }

    override suspend fun fetchMunicipality(subDistrictId: Int): Flow<ApiResult<List<MunicipalityApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchMunicipality(subDistrictId)
            },
            mapper = municipalityApiMapper
        )
    }

    override suspend fun fetchAllBeneficiary(muniId: Int): Flow<ApiResult<List<BeneficiaryApiEntity>>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchUserList(muniId)
            },
            mapper = allBeneficiaryApiMapper
        )
    }

    override suspend fun fetchBeneficiary(params: FetchBeneficiaryApiUseCase.Params): Flow<ApiResult<BeneficiaryApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.fetchSingleUserProfile(
                    nid = params.nidOrSerial,
                    id = params.municipalityId
                )
            },
            mapper = beneficiaryApiMapper
        )
    }

    override suspend fun photoUpdate(params: UpdatePhotoApiUseCase.Params): Flow<ApiResult<UpdatePhotoApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.updateUserImage(
                    serialNo = Utils.mConverter(params.nidOrSerial),
                    image = Utils.mConvertImg(params.image,"image")
                )
            },
            mapper = updatePhotoApiMapper
        )
    }

    override suspend fun updateBeneficiary(params: UpdateBeneficiaryApiUseCase.Params): Flow<ApiResult<UpdateBeneficiaryApiEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.updateUserData(
                    serialNo = params.serial,
                    wordNo = params.wordNo,
                    village = params.village,
                    name = params.name,
                    nidNo = params.nid,
                    phone = params.phone,
                    dateOfBirth = params.dateOfBirth,
                    pesha = params.occupation,
                    fatherOrHusbandName = params.fatherOrHusband,
                    fatherNid = params.fatherNid,
                    husbandWifeNid = params.husbandOrWifeNid,
                    motherName = params.motherName
                )
            },
            mapper = updateBeneficiaryApiMapper
        )
    }
}