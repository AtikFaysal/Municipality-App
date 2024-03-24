package com.pourosova.data.core.domain.apiusecase.auth

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseNonParams
import com.pourosova.data.core.model.apientity.auth.LogoutApiEntity
import com.pourosova.data.core.model.apientity.auth.ProfileApiEntity
import javax.inject.Inject

class LogoutApiUseCase @Inject constructor(
    private val repository: AppRepository
): ApiUseCaseNonParams<LogoutApiEntity> {
    override suspend fun execute() = repository.logout()
}
