package com.pourosova.data.core.domain.apiusecase.auth

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiResult
import com.pourosova.data.core.domain.base.ApiUseCaseParams
import com.pourosova.data.core.domain.base.DataValidationResult
import com.pourosova.data.core.domain.base.IoValidation
import com.pourosova.data.core.domain.base.LoginIoResult
import com.pourosova.data.core.model.apientity.auth.LoginApiEntity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class LoginApiUseCase @Inject constructor(
    private val repository: AppRepository,
    private val ioValidation: IoValidation
) : ApiUseCaseParams<LoginApiUseCase.Params, LoginApiEntity> {
    val ioError = Channel<LoginIoResult>()

    data class Params(
        val email: String,
        val password: String,
    )

    override suspend fun execute(params: Params): Flow<ApiResult<LoginApiEntity>> {

        return when (val result = ioValidation.loginDataValidation(params)) {
            is DataValidationResult.Failure<*> -> {
                ioError.send(result.ioErrorResult as LoginIoResult)
                emptyFlow()
            }

            is DataValidationResult.Success -> repository.login(params = params)
        }
    }
}