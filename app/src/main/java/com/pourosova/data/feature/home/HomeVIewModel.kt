package com.pourosova.data.feature.home

import com.pourosova.data.core.domain.apiusecase.auth.LoginApiUseCase
import com.pourosova.data.core.domain.apiusecase.auth.LogoutApiUseCase
import com.pourosova.data.core.domain.apiusecase.utils.FetchDistrictApiUseCase
import com.pourosova.data.core.domain.apiusecase.utils.FetchDivisionsApiUseCase
import com.pourosova.data.core.domain.apiusecase.utils.FetchMunicipalityApiUseCase
import com.pourosova.data.core.domain.apiusecase.utils.FetchSubDistrictApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.BaseViewModel
import com.pourosova.data.core.model.apientity.utils.DistrictApiEntity
import com.pourosova.data.core.model.apientity.utils.DivisionApiEntity
import com.pourosova.data.core.model.apientity.utils.MunicipalityApiEntity
import com.pourosova.data.core.model.apientity.utils.SubDistrictApiEntity
import com.pourosova.data.sharedpref.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeVIewModel @Inject constructor(
    private val fetchDivisionsApiUseCase: FetchDivisionsApiUseCase,
    private val fetchDistrictApiUseCase: FetchDistrictApiUseCase,
    private val fetchSubDistrictApiUseCase: FetchSubDistrictApiUseCase,
    private val fetchMunicipalityApiUseCase: FetchMunicipalityApiUseCase,
    private val logoutApiUseCase: LogoutApiUseCase,
    private val sharedPrefHelper: SharedPrefHelper
) : BaseViewModel(){
    val action: (UiAction) -> Unit
    private val _uiState =  MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    var divisionId : Int = -1
    var districtId : Int = -1
    var subDistrictId : Int = -1
    var municipalityId : Int = -1

    init {
        action = {
            when(it){
                is UiAction.FetchDistrict -> fetchDistrict(it.divisionId)
                UiAction.FetchDivision -> fetchDivision()
                is UiAction.FetchMunicipality -> fetchMunicipality(it.subDistrictId)
                is UiAction.FetchSubDistrict -> fetchSubDistrict(it.districtId)
                UiAction.Logout -> logout()
            }
        }
        fetchDivision()
    }

    private fun fetchDivision(){
        execute {
            fetchDivisionsApiUseCase.execute().collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.Error(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> {
                        if(it.data.isNotEmpty())
                            _uiState.value = UiState.Division(it.data)
                        else _uiState.value = UiState.Error("No data found")
                    }
                }
            }
        }
    }

    private fun fetchDistrict(divisionId : Int){
        execute {
            fetchDistrictApiUseCase.execute(FetchDistrictApiUseCase.Params(divisionId)).collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.Error(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> {
                        if(it.data.isNotEmpty())
                            _uiState.value = UiState.District(it.data)
                        else _uiState.value = UiState.Error("No data found")
                    }
                }
            }
        }
    }

    private fun fetchSubDistrict(districtId : Int){
        execute {
            fetchSubDistrictApiUseCase.execute(FetchSubDistrictApiUseCase.Params(districtId)).collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.Error(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> {
                        if(it.data.isNotEmpty())
                            _uiState.value = UiState.SubDistrict(it.data)
                        else _uiState.value = UiState.Error("No data found")
                    }
                }
            }
        }
    }

    private fun fetchMunicipality(subDistrictId : Int){
        execute {
            fetchMunicipalityApiUseCase.execute(FetchMunicipalityApiUseCase.Params(subDistrictId)).collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.Error(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> {
                        if(it.data.isNotEmpty())
                            _uiState.value = UiState.Municipality(it.data)
                        else _uiState.value = UiState.Error("No data found")
                    }
                }
            }
        }
    }

    private fun logout(){
        execute {
            logoutApiUseCase.execute().collect{
                when(it){
                    is ApiResult.Error -> _uiEvent.send(UiEvent.NavigateToLogin)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> {
                        sharedPrefHelper.clearAllCache()
                        _uiEvent.send(UiEvent.NavigateToLogin)
                    }
                }
            }
        }
    }
}

sealed class UiState{
    data class Division(val items : List<DivisionApiEntity>): UiState()
    data class District(val items : List<DistrictApiEntity>): UiState()
    data class SubDistrict(val items : List<SubDistrictApiEntity>): UiState()
    data class Municipality(val items : List<MunicipalityApiEntity>): UiState()
    data class Error(val message : String ) : UiState()
    data class Loading(val loading:Boolean): UiState()
}

sealed interface UiEvent{
    data object NavigateToLogin : UiEvent
}

sealed class UiAction {
    data object FetchDivision: UiAction()
    data object Logout: UiAction()
    data class FetchDistrict(val divisionId : Int): UiAction()
    data class FetchSubDistrict(val districtId : Int): UiAction()
    data class FetchMunicipality(val subDistrictId : Int): UiAction()
}