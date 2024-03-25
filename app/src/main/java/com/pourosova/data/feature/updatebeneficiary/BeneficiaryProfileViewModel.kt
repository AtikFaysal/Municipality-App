package com.pourosova.data.feature.updatebeneficiary

import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchAllBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdateBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdatePhotoApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.BaseViewModel
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BeneficiaryProfileViewModel @Inject constructor(
    private val updateBeneficiaryApiUseCase : UpdateBeneficiaryApiUseCase,
    private val updatePhotoApiUseCase : UpdatePhotoApiUseCase,
): BaseViewModel(){

    val action: (UiAction) -> Unit

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(false))
    val uiState get() = _uiState

    init {
        action = {
            when(it){
                is UiAction.UpdatePhoto -> updatePhotoBeneficiary(it.params)
                is UiAction.UpdateBeneficiary -> updateBeneficiary(it.params)
            }
        }
    }

    private fun updateBeneficiary(params : UpdateBeneficiaryApiUseCase.Params){
        execute {
            updateBeneficiaryApiUseCase.execute(params).collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.ApiError(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> uiState.value = UiState.ApiSuccess
                }
            }
        }
    }

    private fun updatePhotoBeneficiary(params : UpdatePhotoApiUseCase.Params){
        Timber.e("params: ${params.image}")
        execute {
            updatePhotoApiUseCase.execute(params).collect{
                when(it){
                    is ApiResult.Error -> _uiState.value = UiState.ApiError(it.message)
                    is ApiResult.Loading -> _uiState.value = UiState.Loading(it.loading)
                    is ApiResult.Success -> _uiState.value = UiState.PhotoApiSuccess(it.data.imageUrl)
                }
            }
        }
    }
}

sealed interface UiState{
    data class Loading(val loading:Boolean): UiState
    data object ApiSuccess : UiState
    data class PhotoApiSuccess(val image : String) : UiState
    data class ApiError(val message : String ) : UiState
}

sealed class UiAction{
    data class UpdateBeneficiary(val params: UpdateBeneficiaryApiUseCase.Params) : UiAction()
    data class UpdatePhoto(val params: UpdatePhotoApiUseCase.Params) : UiAction()
}