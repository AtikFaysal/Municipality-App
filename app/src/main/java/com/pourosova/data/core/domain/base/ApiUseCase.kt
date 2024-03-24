package com.pourosova.data.core.domain.base

import kotlinx.coroutines.flow.Flow

interface ApiUseCaseParams<Params, Type> : BaseUseCase {
    suspend fun execute(params: Params): Flow<ApiResult<Type>>
}

interface ApiUseCaseNonParams<Type> : BaseUseCase {
    suspend fun execute(): Flow<ApiResult<Type>>
}