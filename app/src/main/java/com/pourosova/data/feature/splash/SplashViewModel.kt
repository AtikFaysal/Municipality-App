package com.pourosova.data.feature.splash

import com.pourosova.data.core.domain.apiusecase.auth.FetchProfileApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.BaseViewModel
import com.pourosova.data.sharedpref.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchProfileApiUseCase: FetchProfileApiUseCase,
    private val sharedPrefHelper: SharedPrefHelper
): BaseViewModel(){

    val action: (ProfileUiAction) -> Unit = {
        when(it){
            is ProfileUiAction.PerformProfileApiAction -> fetchProfile()
        }
    }

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    private fun fetchProfile(){
        execute {
            fetchProfileApiUseCase.execute().collect{ result->
                when(result) {
                    is ApiResult.Success -> _uiEvent.send(ProfileUiEvent.NavigateToHome)

                    is ApiResult.Error -> {
                        if(result.code == 401 || result.code == 404) {
                            sharedPrefHelper.clearAllCache()
                            _uiEvent.send(ProfileUiEvent.NavigateToLogin(result.message))
                        }
                        else _uiEvent.send(ProfileUiEvent.ProfileApiError(result.message))
                    }

                    is ApiResult.Loading -> _uiEvent.send(ProfileUiEvent.Loading(result.loading))
                }
            }
        }
    }
}

sealed interface ProfileUiEvent{
    data class Loading(val loading: Boolean) : ProfileUiEvent
    data object NavigateToHome : ProfileUiEvent
    data class ProfileApiError(val message : String ) : ProfileUiEvent
    data class NavigateToLogin(val message : String ) : ProfileUiEvent
}

sealed class ProfileUiAction{
    object PerformProfileApiAction : ProfileUiAction()
}
