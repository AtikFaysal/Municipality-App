package com.pourosova.data.feature.login

import com.pourosova.data.core.domain.apiusecase.auth.FetchProfileApiUseCase
import com.pourosova.data.core.domain.apiusecase.auth.LoginApiUseCase
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.BaseViewModel
import com.pourosova.data.core.model.apientity.auth.LoginApiEntity
import com.pourosova.data.sharedpref.SharedPrefHelper
import com.pourosova.data.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApiUseCase: LoginApiUseCase,
    private val fetchProfileApiUseCase: FetchProfileApiUseCase,
    private val sharedPrefHelper : SharedPrefHelper
): BaseViewModel(){
    val loginError get() = loginApiUseCase.ioError.receiveAsFlow()

    val action: (LoginUiAction) -> Unit = {
        when(it){
            is LoginUiAction.PerformLoginApiAction -> performLoginApi(it.params)
            LoginUiAction.FetchProfile -> fetchProfile()
        }
    }

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent get() = _uiEvent.receiveAsFlow()

    private fun performLoginApi(params: LoginApiUseCase.Params){
        execute {
            loginApiUseCase.execute(params).collect { result->
                when(result) {
                    is ApiResult.Success -> this handleLoginSuccess result.data
                    is ApiResult.Loading -> _uiEvent.send(LoginUiEvent.Loading(result.loading))
                    is ApiResult.Error -> _uiEvent.send(LoginUiEvent.LoginApiError(result.message))
                }
            }
        }
    }

    //this method only called when login is successful
    private infix fun handleLoginSuccess(result : LoginApiEntity){
        execute {
            sharedPrefHelper.putBool(SpKey.loginStatus, true)
            sharedPrefHelper.putString(SpKey.authToken, result.accessToken)
            sharedPrefHelper.putInt(SpKey.tokenExpireAt, result.expiresAt)
            fetchProfile()
        }
    }

    private fun fetchProfile(){
        execute {
            fetchProfileApiUseCase.execute().collect{ result->
                when(result) {
                    is ApiResult.Success -> _uiEvent.send(LoginUiEvent.ProfileApiSuccess)
                    is ApiResult.Error -> _uiEvent.send(LoginUiEvent.ProfileApiError(result.message))
                    is ApiResult.Loading -> _uiEvent.send(LoginUiEvent.Loading(result.loading))
                }
            }
        }
    }
}

sealed interface LoginUiEvent{
    data class Loading(val loading: Boolean) : LoginUiEvent
    data object ProfileApiSuccess : LoginUiEvent
    data class ProfileApiError(val message : String ) : LoginUiEvent
    data class LoginApiError(val message : String ) : LoginUiEvent
}

sealed interface LoginUiAction{
    data class PerformLoginApiAction(val params: LoginApiUseCase.Params) : LoginUiAction
    data object FetchProfile : LoginUiAction
}
