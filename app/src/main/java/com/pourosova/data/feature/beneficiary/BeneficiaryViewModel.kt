package com.pourosova.data.feature.beneficiary

import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchAllBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchBeneficiaryApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.BaseViewModel
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class BeneficiaryViewModel @Inject constructor(
    private val fetchAllBeneficiaryApiUseCase : FetchAllBeneficiaryApiUseCase,
    private val fetchBeneficiaryApiUseCase : FetchBeneficiaryApiUseCase,
): BaseViewModel(){

    val action: (UiAction) -> Unit

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState get() = _uiState

    init {
        action = {
            when(it){
                is UiAction.FetchAllBeneficiary -> fetchAllBeneficiaryList(it.params)
                is UiAction.FetchBeneficiary -> fetchBeneficiary(it.params)
            }
        }
    }

    private fun fetchAllBeneficiaryList(params : FetchAllBeneficiaryApiUseCase.Params){
        execute {
            fetchAllBeneficiaryApiUseCase.execute(params).collect{result->
                when(result){
                    is ApiResult.Success -> {
                        if(result.data.isNotEmpty())
                            _uiState.value = UiState.ApiSuccess(result.data)
                        else _uiState.value = UiState.DataEmpty
                    }
                    is ApiResult.Error -> _uiState.value = UiState.ApiError(result.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(result.loading)
                }
            }
        }
    }

    private fun fetchBeneficiary(params : FetchBeneficiaryApiUseCase.Params){
        execute {
            fetchBeneficiaryApiUseCase.execute(params).collect{result->
                when(result){
                    is ApiResult.Success -> _uiState.value = UiState.ApiSuccess(listOf(result.data))
                    is ApiResult.Error -> _uiState.value = UiState.ApiError(result.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(result.loading)
                }
            }
        }
    }
}

sealed interface UiState{
    data class Loading(val loading:Boolean): UiState
    data class ApiSuccess(val content : List<BeneficiaryApiEntity>) : UiState
    data class ApiError(val message : String ) : UiState
    data object DataEmpty : UiState
}

sealed class UiAction{
    data class FetchAllBeneficiary(val params: FetchAllBeneficiaryApiUseCase.Params) : UiAction()
    data class FetchBeneficiary(val params: FetchBeneficiaryApiUseCase.Params) : UiAction()
}