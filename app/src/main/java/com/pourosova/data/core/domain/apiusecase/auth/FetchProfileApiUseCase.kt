package com.pourosova.data.core.domain.apiusecase.auth

import com.pourosova.data.core.domain.AppRepository
import com.pourosova.data.core.domain.base.ApiUseCaseNonParams
import com.pourosova.data.core.model.apientity.auth.ProfileApiEntity
import javax.inject.Inject

class FetchProfileApiUseCase @Inject constructor(
    private val repository: AppRepository
): ApiUseCaseNonParams<ProfileApiEntity> {
    override suspend fun execute() = repository.fetchProfile()
}

